package com.backend.blog.payloads;

import com.backend.blog.entities.Category;
import com.backend.blog.entities.Comment;
import com.backend.blog.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;
    private String postTitle;
    private String postContent;
    private String imageName;
    private Timestamp createdDate;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();
}
