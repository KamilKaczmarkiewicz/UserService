package com.kams.UserService.user.controller;

import com.kams.UserService.user.dto.CreateUserDto;
import com.kams.UserService.user.dto.UpdateUserDto;
import com.kams.UserService.user.entity.User;
import com.kams.UserService.user.mapper.UserMapper;
import com.kams.UserService.user.service.UserService;
import com.kams.UserService.user.utils.PaginatedResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


/**
 * REST controller for user resource. Doesn't return entity objects but dto objects.
 */
@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

//todo check what we return
//todo use everywhere dto
//todo tests

    /**
     * @return list of users in JSON with pagination info
     */
    @GetMapping
    public ResponseEntity getUsers(@RequestParam(name = "page", defaultValue = "0") int page
            ,@RequestParam(name = "size", defaultValue = "3") int size
            ,@RequestParam(name = "sort", defaultValue = "userName") String sort
            ,@RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection
            ,HttpServletRequest request){
        Page<User> users = userService.findAll(page, size, sort, sortDirection);
        Map<String, Object> response = PaginatedResponseFactory.paginateGetUsersResponse(users, request);
        return ResponseEntity
                .ok(response);
    }

    /**
     * @param request - new user as CreateUserDto
     * @return created user in JSON
     */
    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody CreateUserDto request) {
        User user = userService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserMapper.UserToUserDto(user));
    }

    /**
     * @param request - UpdateUserDto
     * @param id - id of the user to update
     * @return updated user in JSON
     */
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable("id") long id,
                                              @RequestBody UpdateUserDto request){
        Optional<User> user = userService.find(id);
        if (user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        User updatedUser = userService.update(UserMapper.UpdateUserDtoToUser(user.get(), request));
        return ResponseEntity.ok(UserMapper.UserToUserDto(updatedUser));
    }
}
