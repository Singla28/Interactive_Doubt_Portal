package com.backend.blog.payloads;

import com.backend.blog.entities.Comment;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String id;
    @NotEmpty
    @Size(min =4, message= "Username must be min of 4 Characters")
    private String name;
    @Email(message = "Email is not valid")
    private String email;
    @NotEmpty
    @Size(min=3,max=10, message = "Password must be min of 3 chars and max of 10 chars")
    @Pattern(regexp = "a-zA-z1-9")
    private String password;
    @NotEmpty
    private String about;
    private Set<CommentDto> comments= new HashSet<>();
}
