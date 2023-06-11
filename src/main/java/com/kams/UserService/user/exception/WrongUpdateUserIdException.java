package com.kams.UserService.user.exception;

public class WrongUpdateUserIdException extends RuntimeException{

    public WrongUpdateUserIdException(long id, long newId) {
        super("Trying to update user with id: " + id + ", when id in updated user is: " + newId);
    }
}
