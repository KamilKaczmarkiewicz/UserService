package com.kams.UserService.note.exception;

public class NoteNotFoundException extends RuntimeException{

    public NoteNotFoundException(long id) {
        super("Note with id: " + id + " not found!");
    }
}
