package com.example.demo.controller;

import com.example.demo.dto.WishListItemDTO;
import com.example.demo.dto.WishlistCountDTO;
import com.example.demo.model.Wishlist;
import com.example.demo.model.WishlistItem;
import com.example.demo.repository.WishlistItemRepository;
import com.example.demo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Map<String, String>> createOrUpdateWishlist(@PathVariable Long userId, @RequestBody WishListItemDTO wishlistItem) {
        return ResponseEntity.ok(wishlistService.createOrUpdateWishlist(userId, wishlistItem));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Wishlist> getWishlist(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.getWishlistByUserId(userId));
    }

//    @PostMapping("/{wishlistId}/add-item")
//    public ResponseEntity<WishlistItem> addItem(@PathVariable Long wishlistId, @RequestBody WishlistItem item) {
//        return ResponseEntity.ok(wishlistService.addItemToWishlist(wishlistId, item));
//    }

//    @DeleteMapping("/remove-item/{itemId}")
//    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
//        wishlistService.removeItemFromWishlist(itemId);
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlist(wishlistId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<WishlistCountDTO> getCountWishlistProducts(@PathVariable Long userId) {
        Wishlist wishlist = wishlistService.getWishlistByUserId(userId);
        long count = 0;
        if (wishlist != null) {
            List<WishlistItem> wishlistItems = wishlistItemRepository.findByWishlistId(wishlist.getId());
            count = wishlistItems.size();
        }
        WishlistCountDTO response = new WishlistCountDTO();
        response.setCount(count);
        return ResponseEntity.ok(response);
    }
}

