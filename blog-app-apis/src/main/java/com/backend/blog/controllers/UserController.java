package com.backend.blog.controllers;

import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.UserDto;
import com.backend.blog.payloads.UserResponse;
import com.backend.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") Integer id){
        UserDto updateUser = this.userService.updateUser(userDto,id);
        return ResponseEntity.ok(updateUser);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Integer id){
        this.userService.deleteUser(id);
        return new ResponseEntity(new ApiResponse("User not deleted",true), HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id){
        return ResponseEntity.ok(this.userService.getUserById(id));
    }
    @GetMapping("/")
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam(value="pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    ){
        return ResponseEntity.ok(this.userService.getAllUsers(pageNumber,pageSize));
    }
}
