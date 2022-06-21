package uz.mh.talkoncopy.validators.user.user;

import org.springframework.stereotype.Component;
import uz.mh.talkoncopy.dto.user.user.UserCreateDto;
import uz.mh.talkoncopy.dto.user.user.UserUpdateDto;
import uz.mh.talkoncopy.validators.base.AbstractValidator;

import javax.validation.ValidationException;

@Component
public class UserValidator extends AbstractValidator<UserCreateDto, UserUpdateDto,String> {
    @Override
    public void validateKey(String id) throws ValidationException {

    }

    @Override
    public void validOnCreate(UserCreateDto dto) throws ValidationException {

    }

    @Override
    public void validOnUpdate(UserUpdateDto dto) throws ValidationException {

    }
}
