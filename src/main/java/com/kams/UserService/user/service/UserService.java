package com.kams.UserService.user.service;


import com.kams.UserService.user.dto.CreateUserDto;
import com.kams.UserService.user.entity.User;
import com.kams.UserService.user.mapper.UserMapper;
import com.kams.UserService.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service layer for all business actions regarding user entity.
 */
@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    /**
     * @return List of all users in Page
     */
    public Page<User> findAll(int page, int size, String sort, String sortDirection) {//desc
        Order order;
        if(sortDirection.equals("desc")){
            order = new Order(Sort.Direction.DESC, sort);
        }else{
            order = new Order(Sort.Direction.ASC, sort);
        }
        Pageable pr = PageRequest.of(page,size, Sort.by(order));
        Page<User> all = userRepository.findAll(pr);
        return all;
    }

    /**
     * Find single user by ID
     *
     * @param id - user's id
     * @return container with user
     */
    public Optional<User> find(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Create new user.
     *
     * @param userDto - new user
     */
    public User create(CreateUserDto userDto) {
        User user = UserMapper.CreateUserDtoToUser(userDto);
        return userRepository.save(user);
    }

    /**
     * Updates the user
     *
     * @param user - updated user
     */
    public User update(User user){
        return userRepository.save(user);
    }

}
