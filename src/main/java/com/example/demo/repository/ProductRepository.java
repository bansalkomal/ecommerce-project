package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);

    Optional<Product> findByProductCode(String productCode);

    boolean existsByProductCode(String productCode);

    //    List<Product> findByCategory(String category);
//    List<Product> findByCategoryAndBrand(String category, String brand);
    List<Product> findBySubCategoryItemId(Long subCategoryItemId);


    List<Product> findBySubCategoryItem_SubCategory_Category_IdAndSubCategoryItem_SubCategory_Id(Long categoryId, Long subCategoryId);

    List<Product> findBySubCategoryItem_SubCategory_Category_IdAndSubCategoryItem_SubCategory_IdAndSubCategoryItem_Id(Long categoryId, Long subCategoryId, Long subCategoryItemId);

    @Query("SELECT p FROM Product p " +
            "JOIN p.subCategoryItem sci " +
            "JOIN sci.subCategory sc " +
            "JOIN sc.category c " +
            "WHERE c.id = :categoryId AND sc.id = :subCategoryId")
    List<Product> findByCategoryAndSubCategory(@Param("categoryId") Long categoryId,
                                               @Param("subCategoryId") Long subCategoryId);

    @Query("SELECT p FROM Product p " +
            "JOIN p.subCategoryItem sci " +
            "JOIN sci.subCategory sc " +
            "JOIN sc.category c " +
            "WHERE c.id = :categoryId AND sc.id = :subCategoryId AND sci.id = :subCategoryItemId")
    List<Product> findByCategoryAndSubCategoryAndSubCategoryItem(@Param("categoryId") Long categoryId,
                                                                 @Param("subCategoryId") Long subCategoryId,
                                                                 @Param("subCategoryItemId") Long subCategoryItemId);

    @Query("SELECT p FROM Product p WHERE " +
            "(p.price >= :minPrice OR :minPrice IS NULL) AND " +
            "(p.price <= :maxPrice OR :maxPrice IS NULL) AND " +
            "(p.brand = :brand OR :brand IS NULL) AND " +
            "(p.color = :color OR :color IS NULL) AND " +
            "(p.size = :size OR :size IS NULL) AND " +
            "(p.subCategoryItem.id = :subCategoryItemId OR :subCategoryItemId IS NULL)")
    List<Product> filterProducts(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("brand") String brand,
            @Param("color") String color,
            @Param("size") String size,
            @Param("subCategoryItemId") Long subCategoryItemId
    );
}

