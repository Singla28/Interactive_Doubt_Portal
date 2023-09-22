package com.backend.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Integer postId;
    @Column(name="post_title", length = 100, nullable = false)
    private String postTitle;
    @Column(name="content", length = 200, nullable = false)
    private String postContent;
    @Column(name = "image_name")
    private String imageName;
    private Timestamp createdDate;
    @ManyToOne
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}
