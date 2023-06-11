package com.kams.UserService.user.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(long id) {
        super("User with id: " + id + " not found!");
    }
}
