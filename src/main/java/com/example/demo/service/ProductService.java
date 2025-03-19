package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.SubCategory;
import com.example.demo.model.SubCategoryItem;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SubCategoryItemRepository;
import com.example.demo.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private SubCategoryItemRepository subCategoryItemRepository;

//    public Product addProduct(Product product) {
//        return productRepository.save(product);
//    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

//    public Product updateProduct(Product product) {
//        return productRepository.save(product);
//    }


    public Product saveOrUpdateProduct(ProductDTO dto) {
        Product product;
        if (dto.getId() != null) {
            product = productRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            product.setProductCode(dto.getProductCode());
        } else {
            product = new Product();
            String generatedCode = generateUniqueProductCode();
            product.setProductCode(generatedCode);
        }
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setBrand(dto.getBrand());
        product.setImageUrl(dto.getImageUrl());
        product.setColor(dto.getColor());
        //product.setProductCode(dto.getProductCode());
        product.setSize(dto.getSize());

        SubCategoryItem subCategory = subCategoryItemRepository.findById(dto.getSubCategoryItemId())
                .orElseThrow(() -> new RuntimeException("SubCategoryItem not found"));
        product.setSubCategoryItem(subCategory);

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContaining(query);
    }

    public List<Product> filterProducts(Double minPrice, Double maxPrice, String brand, String color, String size, Long subCategoryItemId) {
        return productRepository.filterProducts(minPrice, maxPrice, brand, color, size, subCategoryItemId);
    }

//    public List<Product> filterProducts(String category, String brand) {
//        return productRepository.findByCategoryAndBrand(category, brand);
//    }

    public List<Product> getProductsBySubCategoryItemId(Long subCategoryItemId) {
        return productRepository.findBySubCategoryItemId(subCategoryItemId);
    }

    // Fetch all subcategories
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    // Fetch all subcategory items
    public List<SubCategoryItem> getAllSubCategoryItems() {
        return subCategoryItemRepository.findAll();
    }
    // Fetch all categories with nested subcategories and subcategory items
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Fetch products based on category and subcategory
//    public List<Product> getProductsByCategoryAndSubCategory(Long categoryId, Long subCategoryId) {
//        return productRepository.findBySubCategoryItem_SubCategory_Category_IdAndSubCategoryItem_SubCategory_Id(categoryId, subCategoryId);
//    }

    // Fetch products based on category, subcategory and subcategory item
//    public List<Product> getProductsByCategorySubCategoryAndItem(Long categoryId, Long subCategoryId, Long subCategoryItemId) {
//        return productRepository.findBySubCategoryItem_SubCategory_Category_IdAndSubCategoryItem_SubCategory_IdAndSubCategoryItem_Id(categoryId, subCategoryId, subCategoryItemId);
//    }

    // Fetch all categories with subcategories and subcategory items for UI dropdown
    public List<Category> getAllCategoriesWithDetails() {
        return categoryRepository.findAll();
    }

//    public List<Category> getAllCategoriesWithHierarchy() {
//        List<Category> categories = categoryRepository.findAll();
//        for (Category category : categories) {
//            List<SubCategory> subCategories = category.getSubCategories();
//            for (SubCategory subCategory : subCategories) {
//                List<SubCategoryItem> subCategoryItems = subCategory.getSubCategoryItems();
//                subCategory.setSubCategoryItems(subCategoryItems);
//            }
//            category.setSubCategories(subCategories);
//        }
//        return categories;
//    }

    public List<Product> getProductsByCategoryAndSubCategory(Long categoryId, Long subCategoryId) {
        return productRepository.findByCategoryAndSubCategory(categoryId, subCategoryId);
    }

    public List<Product> getProductsByCategoryAndSubCategoryAndSubCategoryItem(Long categoryId, Long subCategoryId, Long subCategoryItemId) {
        return productRepository.findByCategoryAndSubCategoryAndSubCategoryItem(categoryId, subCategoryId, subCategoryItemId);
    }

    private String generateUniqueProductCode() {
        String code;
        do {
            code = "PROD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (productRepository.existsByProductCode(code));
        return code;
    }
}

