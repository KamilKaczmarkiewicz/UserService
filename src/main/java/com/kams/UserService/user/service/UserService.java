package com.kams.UserService.user.service;


import com.kams.UserService.user.entity.User;
import com.kams.UserService.user.exception.UserWithIdNotFoundException;
import com.kams.UserService.user.exception.WrongUpdateUserIdException;
import com.kams.UserService.user.repository.UserRepository;
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
public class UserService {

    private UserRepository userRepository;

    public Page<User> findAll(int page, int size, List<String> sort ) {
        Pageable pr = PageRequest.of(page,size, Sort.by(createSortOrder(sort)));
        Page<User> all = userRepository.findAll(pr);
        return all;
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

    public Optional<User> find(Long id) {
        return userRepository.findById(id);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(User newUser, long id){
        Optional<User> user = find(id);
        if(newUser.getId()==user.orElseThrow(() -> new UserWithIdNotFoundException(id)).getId())
        {
            return userRepository.save(newUser);
        }
        throw new WrongUpdateUserIdException(id, newUser.getId());
    }

}
