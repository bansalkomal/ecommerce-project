package com.example.demo.repository;

import com.example.demo.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
//    boolean existsByWishlistAndProductId(Wishlist wishlist, Product product);
//    void deleteByWishlistAndProductId(Wishlist wishlist, Product product);

    List<WishlistItem>  findByWishlistId(Long wishlistId);

    @Modifying
    @Transactional
    @Query("DELETE FROM WishlistItem wi WHERE wi.wishlist.id = :wishlistId AND wi.product.id IN :productIds")
    void deleteAllByWishlistIdAndProductIds(@Param("wishlistId") Long wishlistId, @Param("productIds") Set<Long> productIds);

}

