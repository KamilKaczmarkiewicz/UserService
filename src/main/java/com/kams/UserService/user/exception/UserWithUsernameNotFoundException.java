package com.kams.UserService.user.exception;

public class UserWithUsernameNotFoundException extends RuntimeException{

    public UserWithUsernameNotFoundException(String username) {
        super("User with username: " + username + " not found!");
    }
}
