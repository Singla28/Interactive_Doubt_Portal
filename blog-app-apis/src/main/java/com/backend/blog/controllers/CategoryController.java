package com.backend.blog.controllers;

import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.CategoryDto;
import com.backend.blog.payloads.CategoryResponse;
import com.backend.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody  CategoryDto categoryDto){
        return new ResponseEntity(this.categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable(name="categoryId") Integer id){
        return new ResponseEntity(this.categoryService.updateCategory(categoryDto,id),HttpStatus.OK);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(name="categoryId") Integer id){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted",true),HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name="categoryId") Integer id){
        return new ResponseEntity<>(this.categoryService.getCategoryById(id),HttpStatus.FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(value="pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value="pageSize", defaultValue = "10", required = false) Integer pageSize
    ){
        return ResponseEntity.ok(this.categoryService.getAllCategories(pageNumber,pageSize));
    }
}
