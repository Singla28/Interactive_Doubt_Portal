package com.backend.blog.services.impl;

import com.backend.blog.entities.User;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.payloads.UserDto;
import com.backend.blog.payloads.UserResponse;
import com.backend.blog.repositories.UserRepo;
import com.backend.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto user) {
        return this.modelMapper.map(this.userRepo.save(this.modelMapper.map(user,User.class)),UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User user = this.userRepo.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("user","id",id));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(user.getAbout());
        return this.modelMapper.map(this.userRepo.save(user),UserDto.class);
    }

    @Override
    public UserDto getUserById(Integer id) {
        return this.modelMapper.map(this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user","id",id)),UserDto.class);
    }

    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber,pageSize);
        Page<User> pageUser = this.userRepo.findAll(page);
        UserResponse userResponse = new UserResponse();
        userResponse.setContent(pageUser.getContent().stream().map(x->this.modelMapper.map(x,UserDto.class)).collect(Collectors.toList()));
        userResponse.setPageNumber(pageUser.getNumber());
        userResponse.setPageSize(pageUser.getSize());
        userResponse.setTotalPages(pageUser.getTotalPages());
        userResponse.setTotalElements(pageUser.getTotalElements());
        return userResponse;
    }

    @Override
    public void deleteUser(Integer id) {
        User user = this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user","id",id));
        this.userRepo.delete(user);
    }
}
