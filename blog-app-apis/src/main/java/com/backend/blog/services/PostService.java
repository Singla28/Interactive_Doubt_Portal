package com.backend.blog.services;

import com.backend.blog.payloads.PostDto;
import com.backend.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer id);
    void deletePost(Integer id);
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy);
    PostDto getPostById(Integer id);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
    List<PostDto> searchPosts(String keyword);
}
