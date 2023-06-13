package com.kams.UserService.note.controller;

import com.kams.UserService.note.entity.Note;
import com.kams.UserService.note.exception.NoteNotFoundException;
import com.kams.UserService.note.requestDto.CreateNoteRequest;
import com.kams.UserService.note.requestDto.mapper.NoteRequestMapper;
import com.kams.UserService.note.service.NoteService;
import com.kams.UserService.note.view.NoteModel;
import com.kams.UserService.note.view.NoteModelAssembler;
import com.kams.UserService.user.exception.UserNotFoundException;
import com.kams.UserService.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@AllArgsConstructor
public class NoteController {

    private NoteService noteService;

    private UserService userService;

    private NoteModelAssembler noteModelAssembler;

    private PagedResourcesAssembler pagedResourcesAssembler;

    @GetMapping
    public PagedModel getNotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "title,asc") List<String> sort) {
        Page<Note> notes = noteService.findAll(page, size, sort);
        return pagedResourcesAssembler.toModel(notes, noteModelAssembler);
    }

    @GetMapping("/{id}")
    public NoteModel getNote(@PathVariable("id") long id){
        Note note = noteService.find(id).orElseThrow(() -> new NoteNotFoundException(id));
        return noteModelAssembler.toModel(note);
    }

    @PostMapping
    public ResponseEntity createNote (@RequestBody CreateNoteRequest request) {
        Note note = NoteRequestMapper.INSTANCE.createNoteRequestToNote(request);
        note.setUser(userService.find(request.user_id()).orElseThrow(() -> new UserNotFoundException(request.user_id())));
        note = noteService.create(note);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        "{\"Id\": " + note.getId() + "}"
                );
    }
}
