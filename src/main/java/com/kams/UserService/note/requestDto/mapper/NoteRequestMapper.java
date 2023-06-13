package com.kams.UserService.note.requestDto.mapper;

import com.kams.UserService.note.entity.Note;
import com.kams.UserService.note.requestDto.CreateNoteRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface NoteRequestMapper {

    NoteRequestMapper INSTANCE = Mappers.getMapper(NoteRequestMapper.class);

    Note createNoteRequestToNote(CreateNoteRequest createNoteRequest);

}
