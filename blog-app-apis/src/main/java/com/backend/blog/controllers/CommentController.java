package com.backend.blog.controllers;

import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.CommentDto;
import com.backend.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/{userId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId, @PathVariable Integer userId){
        return ResponseEntity.ok(this.commentService.createComment(commentDto,postId,userId));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        return new ResponseEntity<>(new ApiResponse("Comment Deleted Successfully", true), HttpStatus.OK);

    }
}
