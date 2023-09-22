package com.backend.blog.services.impl;

import com.backend.blog.entities.Comment;
import com.backend.blog.entities.Post;
import com.backend.blog.entities.User;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.payloads.CommentDto;
import com.backend.blog.repositories.CommentRepo;
import com.backend.blog.repositories.PostRepo;
import com.backend.blog.repositories.UserRepo;
import com.backend.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        return this.modelMapper.map(comment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment", "id",commentId));
        this.commentRepo.delete(comment);
    }
}
