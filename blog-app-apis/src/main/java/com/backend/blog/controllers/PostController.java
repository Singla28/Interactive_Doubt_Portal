package com.backend.blog.controllers;

import com.backend.blog.config.AppConstants;
import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.PostDto;
import com.backend.blog.payloads.PostResponse;
import com.backend.blog.services.FileService;
import com.backend.blog.services.PostService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/userId/{userId}/categoryId/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
        return new ResponseEntity<>(this.postService.createPost(postDto,userId,categoryId), HttpStatus.CREATED);
    }

    @GetMapping("/userId/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        return new ResponseEntity<>(this.postService.getPostsByUser(userId),HttpStatus.FOUND);
    }

    @GetMapping("/categoryId/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        return new ResponseEntity<>(this.postService.getPostsByCategory(categoryId),HttpStatus.FOUND);
    }
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value ="sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy){
        return new ResponseEntity<>(this.postService.getAllPosts(pageNumber,pageSize,sortBy),HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer id){
        return ResponseEntity.ok(this.postService.getPostById(id));
    }
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
        return ResponseEntity.ok(this.postService.searchPosts(keyword));
    }
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable(name="postId") Integer id) {
        this.postService.deletePost(id);
        return new ResponseEntity<>(new ApiResponse("Post Deleted", true),HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name="postId") Integer id){
        return ResponseEntity.ok(this.postService.updatePost(postDto,id));
    }

    @PostMapping("/posts/image/upload/{postId}/{userId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                         @PathVariable Integer postId) throws IOException {
        String fileName = this.fileService.uploadImage(path,image);
        PostDto postDto = this.postService.getPostById(postId);
        postDto.setImageName(fileName);

        return new ResponseEntity<PostDto>(this.postService.updatePost(postDto,postId),HttpStatus.OK);
    }

    @GetMapping(value="/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream inputStream = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());
    }
}
