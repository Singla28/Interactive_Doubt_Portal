package com.backend.blog.services.impl;

import com.backend.blog.entities.Category;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.payloads.CategoryDto;
import com.backend.blog.payloads.CategoryResponse;
import com.backend.blog.repositories.CategoryRepo;
import com.backend.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return this.modelMapper.map(this.categoryRepo.save(this.modelMapper.map(categoryDto, Category.class)),CategoryDto.class);

    }
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
        Category category= this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        return this.modelMapper.map(this.categoryRepo.save(category),CategoryDto.class);

    }
    @Override
    public void deleteCategory(Integer id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
        this.categoryRepo.delete(category);
    }
    @Override
    public CategoryDto getCategoryById(Integer id) {
        return this.modelMapper.map(this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","id",id)),CategoryDto.class);

    }
    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber,pageSize);
        Page<Category> pageCategory = this.categoryRepo.findAll(page);
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(pageCategory.getContent()
                .stream().map(x->this.modelMapper.map(x,CategoryDto.class)).collect(Collectors.toList()));
        categoryResponse.setPageNumber(pageCategory.getNumber());
        categoryResponse.setPageSize(pageCategory.getSize());
        categoryResponse.setTotalPages(pageCategory.getTotalPages());
        categoryResponse.setTotalElements(pageCategory.getTotalElements());
        categoryResponse.setLastPage(pageCategory.isLast());
        return categoryResponse;
    }
}
