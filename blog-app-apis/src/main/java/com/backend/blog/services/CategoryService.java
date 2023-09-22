package com.backend.blog.services;

import com.backend.blog.payloads.CategoryDto;
import com.backend.blog.payloads.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer id);
    void deleteCategory(Integer id);
    CategoryDto getCategoryById(Integer id);
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);
}
