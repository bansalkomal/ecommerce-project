package com.example.demo.service;

import com.example.demo.model.SubCategoryItem;
import com.example.demo.repository.SubCategoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryItemService {
    private final SubCategoryItemRepository subCategoryItemRepository;

    public List<SubCategoryItem> getSubCategoryItemsBySubCategoryId(Long subCategoryId) {
        return subCategoryItemRepository.findBySubCategoryId(subCategoryId);
    }
}
