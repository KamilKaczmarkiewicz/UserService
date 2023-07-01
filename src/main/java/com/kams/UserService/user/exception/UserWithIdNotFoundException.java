package com.kams.UserService.user.exception;

public class UserWithIdNotFoundException extends RuntimeException{

    public UserWithIdNotFoundException(long id) {
        super("User with id: " + id + " not found!");
    }
}
