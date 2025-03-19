package com.example.demo.controller;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.model.Review;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public Review addReview(@RequestParam Long productId,
                            @RequestParam Long userId,
                            @RequestBody ReviewDTO reviewDTO) {
        return reviewService.addReview(productId, userId, reviewDTO);
    }

    @GetMapping("/product/{productId}")
    public List<Review> getReviewsByProduct(@PathVariable Long productId) {
        return reviewService.getReviewsByProduct(productId);
    }

    @GetMapping("/product/{productId}/user/{userId}")
    public ResponseEntity<Review> getReviewByProductAndUser(@PathVariable Long productId,
                                                            @PathVariable Long userId) {
        return reviewService.getReviewByProductAndUser(productId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
