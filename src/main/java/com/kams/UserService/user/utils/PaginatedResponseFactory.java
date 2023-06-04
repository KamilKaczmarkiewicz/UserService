package com.kams.UserService.user.utils;

import com.kams.UserService.user.dto.UserDto;
import com.kams.UserService.user.entity.User;
import com.kams.UserService.user.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;
import java.util.Map;

public class PaginatedResponseFactory {

    public static Map<String, Object> paginateGetUsersResponse(Page<User> pageUsers, HttpServletRequest request) {
        Page<UserDto> usersDto = pageUsers.map(UserMapper::UserToUserDto);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("users", usersDto.getContent());
        response.put("last", usersDto.isLast());
        response.put("first", usersDto.isFirst());
        response.put("totalPages", usersDto.getTotalPages());
        response.put("totalElements", usersDto.getTotalElements());
        response.put("currentPage", usersDto.getNumber());
        response.put("size", usersDto.getSize());
        response.put("numberOfElements", usersDto.getNumberOfElements());
        response.put("next", UrlFactory.createNextPageUrl(request, usersDto));
        response.put("previous", UrlFactory.createPreviousPageUrl(request, usersDto));
        return response;
    }


}
