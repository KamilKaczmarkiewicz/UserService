package com.kams.UserService.user.exception;

public class UserWithUsernameAlreadyExist extends RuntimeException{


    public UserWithUsernameAlreadyExist(String username) {
        super("User with username: " + username + " already exists!");
    }
}
