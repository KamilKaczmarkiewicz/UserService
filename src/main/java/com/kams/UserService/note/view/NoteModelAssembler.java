package com.kams.UserService.note.view;

import com.kams.UserService.note.controller.NoteController;
import com.kams.UserService.note.entity.Note;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NoteModelAssembler implements RepresentationModelAssembler<Note, NoteModel> {
    @Override
    public NoteModel toModel(Note entity) {
        NoteModel note = NoteModel.builder()
                .title(entity.getTitle())
                .summary(entity.getSummary())
                .content(entity.getContent())
                .user_id(entity.getUser().getId())
                .build();
        Link link = linkTo(methodOn(NoteController.class).getNote(entity.getId())).withSelfRel();
        note.add(link);
        return note;
    }
}
