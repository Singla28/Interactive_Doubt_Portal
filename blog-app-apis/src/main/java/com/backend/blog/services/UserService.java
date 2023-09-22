package com.backend.blog.services;

import com.backend.blog.entities.User;
import com.backend.blog.payloads.UserDto;
import com.backend.blog.payloads.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer id);
    UserDto getUserById(Integer id);
    UserResponse getAllUsers(Integer pageNumber, Integer pageSize);
    void deleteUser(Integer id);
}
