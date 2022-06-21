package uz.mh.talkoncopy.mappers.user.user;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.mh.talkoncopy.dto.user.user.UserCreateDto;
import uz.mh.talkoncopy.dto.user.user.UserDto;
import uz.mh.talkoncopy.dto.user.user.UserUpdateDto;
import uz.mh.talkoncopy.entities.user.User;
import uz.mh.talkoncopy.mappers.base.AbstractMapper;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<User, UserDto, UserCreateDto, UserUpdateDto> {
    @Override
    UserDto toDto(User entity);

    @Override
    List<UserDto> toDto(List<User> entities);

    @Override
    User fromCreateDto(UserCreateDto createDto);

    @Override
    User fromUpdateDto(UserUpdateDto updateDto);
}
