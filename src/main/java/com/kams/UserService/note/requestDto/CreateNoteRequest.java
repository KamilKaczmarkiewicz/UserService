package com.kams.UserService.note.requestDto;

public record CreateNoteRequest (
        String title,
        String summary,
        String content,
        long user_id
){
}
