package com.kams.UserService.note.service;

import com.kams.UserService.note.entity.Note;
import com.kams.UserService.note.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteService {

    private NoteRepository noteRepository;


    public Page<Note> findAll(int page, int size, List<String> sort) {
        Pageable pr = PageRequest.of(page,size, Sort.by(createSortOrder(sort)));
        Page<Note> notes = noteRepository.findAll(pr);
        return notes;
    }
    private List<Sort.Order> createSortOrder(List<String> sortList) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction = Sort.Direction.fromString(sortList.get(sortList.size() - 1));
        sortList.remove(sortList.size() - 1);
        for (String sort : sortList) {
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    public Optional<Note> find(long id){
        return noteRepository.findById(id);
    }

    public Note create(Note note){
        return noteRepository.save(note);
    }
}
