package uz.mh.talkoncopy.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mh.talkoncopy.controller.AbstractController;
import uz.mh.talkoncopy.dto.response.DataDto;
import uz.mh.talkoncopy.dto.user.user.LoginDto;
import uz.mh.talkoncopy.dto.user.user.SessionDto;
import uz.mh.talkoncopy.services.user.UserService;
@RestController
public class UserController extends AbstractController<UserService> {
    protected UserController(UserService service) {
        super(service);
    }

    @RequestMapping(value = PATH + "/auth/access/token", method = RequestMethod.POST)
    public ResponseEntity<DataDto<SessionDto>> login(@RequestBody LoginDto dto) {
        return service.getToken(dto);
    }

    @RequestMapping(value = PATH + "/auth/register",method = RequestMethod.POST)
    public ResponseEntity<Integer> getCode(@RequestParam String phoneNumber){
        return new ResponseEntity<>(service.getCode(phoneNumber),HttpStatus.OK);
    }
}
