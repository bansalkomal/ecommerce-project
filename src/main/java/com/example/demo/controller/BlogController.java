package com.example.demo.controller;


import com.example.demo.model.Blog;
import com.example.demo.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    // ✅ Create a New Blog (POST)
    @PostMapping("/create")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        Blog savedBlog = blogRepository.save(blog);
        return ResponseEntity.ok(savedBlog);
    }

    // ✅ Get All Blogs (GET)
    @GetMapping("/all")
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    // ✅ Get Blog by ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

