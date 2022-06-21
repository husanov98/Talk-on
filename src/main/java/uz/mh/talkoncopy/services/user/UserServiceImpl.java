package uz.mh.talkoncopy.services.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mh.talkoncopy.dto.response.AppErrorDto;
import uz.mh.talkoncopy.dto.response.DataDto;
import uz.mh.talkoncopy.dto.user.user.LoginDto;
import uz.mh.talkoncopy.dto.user.user.ProfileDto;
import uz.mh.talkoncopy.dto.user.user.SessionDto;
import uz.mh.talkoncopy.dto.user.user.UserDetails;
import uz.mh.talkoncopy.entities.user.User;
import uz.mh.talkoncopy.exceptions.user.UserBlockedException;
import uz.mh.talkoncopy.mappers.user.user.UserMapper;
import uz.mh.talkoncopy.properties.ServerProperty;
import uz.mh.talkoncopy.repositories.user.user.UserRepository;
import uz.mh.talkoncopy.services.base.AbstractService;
import uz.mh.talkoncopy.validators.user.user.UserValidator;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@Transactional
public class UserServiceImpl extends AbstractService<UserRepository, UserMapper, UserValidator> implements UserService, UserDetailsService {
    public static final String ACCOUNT_SID = "AC16a37f0224d16647425d873c8cb5c580";
    public static final String AUTH_TOKEN = "7c95879559d7059682bc74ec547a3a47";

    private final ServerProperty serverProperty;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    private final int BLOCKED_TIME_SECOND = 3600;
    public static int EXPIRY_TIME_SECOND = 3600;

    public UserServiceImpl(ServerProperty serverProperty,
                           ObjectMapper objectMapper,
                           UserMapper mapper,
                           UserRepository repository,
                           UserValidator validator, PasswordEncoder passwordEncoder) {
        super(mapper, validator, repository);
        this.serverProperty = serverProperty;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User findUser = repository.findByPhoneNumberAndDeletedFalse(phoneNumber).get();
        System.out.println(findUser);
        return new uz.mh.talkoncopy.dto.user.user.UserDetails(findUser);
    }

    @Override
    public ResponseEntity<DataDto<SessionDto>> getToken(LoginDto loginDto) {

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(serverProperty.getServerUrl() + "/api/login");
            byte[] bytes = objectMapper.writeValueAsBytes(loginDto);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            httpPost.setHeader("Content-Type", "application/x-www-urlencoded");
            httpPost.setEntity(new InputStreamEntity(byteArrayInputStream));

            HttpResponse response = httpClient.execute(httpPost);
            System.out.println(response.getEntity());
            JsonNode json_auth = objectMapper.readTree(EntityUtils.toString(response.getEntity()));

            if (json_auth.has("success") && json_auth.get("success").asBoolean()) {
                JsonNode node = json_auth.get("data");
                SessionDto sessionDto = objectMapper.readValue(node.toString(), SessionDto.class);
                return new ResponseEntity<>(new DataDto<>(sessionDto), HttpStatus.OK);
            }
            return new ResponseEntity<>(new DataDto<>(objectMapper.readValue(json_auth.get("error").toString(), AppErrorDto.class)), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder()
                    .message(e.getLocalizedMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build()), HttpStatus.OK);
        }
    }

    @Override
    @Transactional(dontRollbackOn = {UserBlockedException.class})
    public int getCode(String phoneNumber) {
        int code = new Random().nextInt(999999);
        log.info(" code : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + code);
//        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
        var optionalUser = repository.findByPhoneNumberAndDeletedFalse(phoneNumber);
        System.err.println(code);
        User user = checkUserToBlock(optionalUser);
//        sendSmsToPhone(phoneNumber,code);
        user.setTryCount(user.getTryCount() + 1);
        user.setPhoneNumber(phoneNumber);
        user.setCode(passwordEncoder.encode(code + ""));
        user.setExpiry(LocalDateTime.now().plusSeconds(EXPIRY_TIME_SECOND));
        repository.save(user);
        return code;
    }

    private void sendSmsToPhone(String phoneNumber, int code) {
        Runnable runnable = () -> {
            Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber("+18144984339"), code + "send this code").create();
            System.out.println(message.getSid());
        };
        new Thread(runnable).start();
    }

    private User checkUserToBlock(Optional<User> optionalUser) {
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (user.getStatus() == 1) {
                if (user.getUpdatedAt().isAfter(LocalDateTime.now().minusSeconds(BLOCKED_TIME_SECOND))) {
                    throw new UserBlockedException("User is blocked");
                } else {
                    user.setStatus((short) 0);
                    user.setTryCount(0);
                }
            } else if (user.getTryCount() >= 3) {
                user.setStatus((short) 1);
                repository.save(user);
                throw new UserBlockedException("User is blocked");
            }
        } else user = new User();
        return user;
    }

    @Override
    public void updateProfile(ProfileDto profileDto) {

    }

    @Override
    public ResponseEntity<?> seeBalance(String id) {
        return null;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
}
