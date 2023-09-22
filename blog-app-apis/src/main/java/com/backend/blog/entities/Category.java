package com.backend.blog.entities;

import com.backend.blog.payloads.CategoryDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Integer categoryId;
    @Column(name="category_title", length = 100, nullable=false)
    private String categoryTitle;
    @Column(name = "category_desc")
    private String categoryDescription;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Post> postList= new ArrayList<>();
}
