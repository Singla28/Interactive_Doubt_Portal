package com.backend.blog.services.impl;

import com.backend.blog.entities.Category;
import com.backend.blog.entities.Post;
import com.backend.blog.entities.User;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.payloads.PostDto;
import com.backend.blog.payloads.PostResponse;
import com.backend.blog.payloads.UserDto;
import com.backend.blog.repositories.CategoryRepo;
import com.backend.blog.repositories.PostRepo;
import com.backend.blog.repositories.UserRepo;
import com.backend.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","id",categoryId));

        Post post = this.postRepo.save(this.modelMapper.map(postDto, Post.class));
        post.setImageName("default.JPG");
        post.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        post.setCategory(category);
        post.setUser(user);

        return this.modelMapper.map(this.postRepo.save(post),PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id",id));
        post.setPostContent(postDto.getPostContent());
        post.setPostTitle(postDto.getPostTitle());
        post.setImageName(postDto.getImageName());
        post.setCreatedDate(postDto.getCreatedDate());

        return this.modelMapper.map(this.postRepo.save(post),PostDto.class);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id",id));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable page = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy).descending());
        Page<Post> pagePost= this.postRepo.findAll(page);
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(pagePost.getContent().stream().map(x->this.modelMapper.map(x,PostDto.class))
                .collect(Collectors.toList()));
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer id) {
        return this.modelMapper.map(this.postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id",id)),PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
       return this.postRepo.findByCategory(this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category","id",categoryId)))
                .stream().map(x->this.modelMapper.map(x,PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        return this.postRepo.findByUser(this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("user","id",userId)))
                .stream().map(x->this.modelMapper.map(x, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return this.postRepo.findByTitleContaining(keyword)
                .stream().map(x->this.modelMapper.map(x,PostDto.class))
                .collect(Collectors.toList());
    }
}
