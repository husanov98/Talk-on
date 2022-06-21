package uz.mh.talkoncopy.services.user;

import org.springframework.http.ResponseEntity;
import uz.mh.talkoncopy.dto.response.DataDto;
import uz.mh.talkoncopy.dto.user.user.LoginDto;
import uz.mh.talkoncopy.dto.user.user.ProfileDto;
import uz.mh.talkoncopy.dto.user.user.SessionDto;
import uz.mh.talkoncopy.services.base.BaseGenericService;

public interface UserService extends BaseGenericService {

    ResponseEntity<DataDto<SessionDto>> getToken(LoginDto loginDto);

    int getCode(String phoneNumber);

    void updateProfile(ProfileDto profileDto);

    ResponseEntity<?> seeBalance(String id);
}
