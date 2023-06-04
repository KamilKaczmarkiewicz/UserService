package com.kams.UserService.user.mapper;


import com.kams.UserService.user.dto.CreateUserDto;
import com.kams.UserService.user.dto.UpdateUserDto;
import com.kams.UserService.user.dto.UserDto;
import com.kams.UserService.user.entity.User;


public class UserMapper {

    public static UserDto UserToUserDto(User user){
        return new UserDto(
                user.getUserName(),
                user.getAge());
    }

    public static User CreateUserDtoToUser(CreateUserDto userDto){
        return User.builder()
                .age(userDto.age())
                .userName(userDto.userName())
                .build();
    }

    public static User UpdateUserDtoToUser(User user, UpdateUserDto userDto){
        user.setUserName(userDto.userName());
        user.setAge(userDto.age());
        return user;
    }
}
