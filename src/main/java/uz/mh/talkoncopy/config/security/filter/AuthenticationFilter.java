package uz.mh.talkoncopy.config.security.filter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import uz.mh.talkoncopy.dto.response.AppErrorDto;
import uz.mh.talkoncopy.dto.response.DataDto;
import uz.mh.talkoncopy.dto.user.user.LoginDto;
import uz.mh.talkoncopy.dto.user.user.SessionDto;
import uz.mh.talkoncopy.dto.user.user.UserDetails;
import uz.mh.talkoncopy.entities.user.User;
import uz.mh.talkoncopy.exceptions.user.UserBlockedException;
import uz.mh.talkoncopy.exceptions.user.UserNotFoundException;
import uz.mh.talkoncopy.repositories.user.user.UserRepository;
import uz.mh.talkoncopy.utils.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper;
    private final UserRepository repository;

    public AuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper mapper, UserRepository repository) {
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
        this.repository = repository;
        super.setFilterProcessesUrl("/api/login");
    }

    @Override
    @Transactional(dontRollbackOn = {UserBlockedException.class, UserNotFoundException.class})
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDto loginDto = mapper.readValue(request.getReader(), LoginDto.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getPhoneNumber(), loginDto.getCode());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {

            throw new RuntimeException();

        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails user = (UserDetails) authResult.getPrincipal();

        Date expiryForAccessToken = JwtUtil.getExpiry();

        Date expiryForRefreshToken = JwtUtil.getExpiryRefreshToken();

        Date issuedAt = new Date();

        String accessToken = JWT.create()
                .withSubject(user.getId())
                .withExpiresAt(expiryForAccessToken)
                .withIssuer(request.getRequestURL().toString())
                .withIssuedAt(issuedAt)
                .withClaim("withClaim", user.getAuthorities().toString())
                .sign(JwtUtil.getAlgorithm());

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiryForRefreshToken)
                .withIssuer(request.getRequestURL().toString())
                .withIssuedAt(issuedAt)
                .sign(JwtUtil.getAlgorithm());

        SessionDto sessionDto = SessionDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiryForAccessToken(expiryForAccessToken.getTime())
                .expiryForRefreshToken(expiryForRefreshToken.getTime())
                .issuedAt(issuedAt.getTime()).build();

        User changeUser = repository.findByPhoneNumberAndDeletedFalse(user.getUsername()).get();
        changeUser.setTryCount(0);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (changeUser.isFirstTime()) {
            response.setStatus(222);
        } else {
            changeUser.setFirstTime(true);
            response.setStatus(223);
        }
        repository.save(changeUser);
        mapper.writeValue(response.getOutputStream(), new DataDto<SessionDto>(sessionDto));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("Error login in: {}", failed.getMessage());
        response.setHeader("error", failed.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        DataDto<AppErrorDto> responseDto = new DataDto<>(new AppErrorDto(failed.getLocalizedMessage(), request.getRequestURI(), HttpStatus.FORBIDDEN, failed.getMessage()));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(),responseDto);
    }
}
