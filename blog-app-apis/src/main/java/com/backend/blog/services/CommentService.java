package com.backend.blog.services;

import com.backend.blog.entities.Comment;
import com.backend.blog.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer post_id, Integer userId);
    void deleteComment(Integer commentId);
}
