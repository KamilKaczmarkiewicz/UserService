package com.kams.UserService.user.controller;

import com.kams.UserService.user.exception.UserNotFoundException;
import com.kams.UserService.user.view.UserModel;
import com.kams.UserService.user.view.UserModelAssembler;
import com.kams.UserService.user.entity.User;
import com.kams.UserService.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private UserModelAssembler userModelAssembler;

    private PagedResourcesAssembler pagedResourcesAssembler;

    @GetMapping
    public PagedModel getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "userName,asc") List<String> sort){
        Page<User> users = userService.findAll(page, size, sort);
        return pagedResourcesAssembler.toModel(users, userModelAssembler);
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody User user) {
        user = userService.create(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        "{\"Id\": " + user.getId() + "}"
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable("id") long id,
                                              @RequestBody User request){
        userService.update(request, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public UserModel getUser(@PathVariable("id") long id){
        Optional<User> user = userService.find(id);
        return userModelAssembler.toModel(user.orElseThrow(() -> new UserNotFoundException(id)));
    }
}
