package com.example.demo.controller;

import com.example.demo.model.SubCategory;
import com.example.demo.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subcategories")
@RequiredArgsConstructor
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @GetMapping
    public ResponseEntity<List<SubCategory>> getSubCategories(@RequestParam Long categoryId) {
        return ResponseEntity.ok(subCategoryService.getSubCategoriesByCategoryId(categoryId));
    }
}
