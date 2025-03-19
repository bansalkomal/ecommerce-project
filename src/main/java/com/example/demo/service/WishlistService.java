package com.example.demo.service;

import com.example.demo.dto.WishListItemDTO;
import com.example.demo.model.Product;
import com.example.demo.model.Wishlist;
import com.example.demo.model.WishlistItem;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.WishlistItemRepository;
import com.example.demo.repository.WishlistRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Autowired
    private ProductRepository productRepository;

//    public Wishlist createOrUpdateWishlist(Long userId, List<WishListItemDTO> wishlistItemsDTO) {
//        Wishlist wishlist = wishlistRepository.findByUserId(userId)
//                .orElseGet(() -> {
//                    Wishlist newWishlist = new Wishlist(userId);
//                    return wishlistRepository.save(newWishlist);
//                });
//
//        return updateWishlistItems(wishlist, wishlistItemsDTO);
//    }
//
//    private Wishlist updateWishlistItems(Wishlist wishlist, List<WishListItemDTO> wishlistItemsDTO) {
//        Set<Long> requestedProductIds = wishlistItemsDTO.stream()
//                .map(WishListItemDTO::getId)
//                .collect(Collectors.toSet());
//
//        List<WishlistItem> existingWishlistItems = wishlistItemRepository.findByWishlistId(wishlist.getId());
//        Set<Long> existingProductIds = existingWishlistItems.stream()
//                .map(item -> item.getProduct().getId())
//                .collect(Collectors.toSet());
//
//        // Identify products to be added and removed
//        Set<Long> productsToAdd = new HashSet<>(requestedProductIds);
//        productsToAdd.removeAll(existingProductIds);
//
//        Set<Long> productsToRemove = new HashSet<>(existingProductIds);
//        productsToRemove.removeAll(requestedProductIds);
//
//        existingWishlistItems.removeIf(item -> productsToRemove.contains(item.getProduct().getId()));
//        wishlistItemRepository.deleteAllByWishlistIdAndProductIds(wishlist.getId(), productsToRemove);
//
//        List<WishlistItem> newItems = new ArrayList<>();
//        // Add new products
//        for (Long productId : productsToAdd) {
//            Product product = productRepository.findById(productId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
//            WishlistItem newItem = new WishlistItem(wishlist, product);
//            existingWishlistItems.add(newItem);
//        }
//
//        wishlistItemRepository.saveAll(newItems);
//        wishlistItemRepository.saveAll(existingWishlistItems);
//
//        wishlistRepository.save(wishlist);
//        return wishlist;
//    }

    @Transactional
    public Map<String, String> createOrUpdateWishlist(Long userId, WishListItemDTO wishlistItemDTO) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Wishlist newWishlist = new Wishlist(userId);
                    return wishlistRepository.save(newWishlist);
                });

        return updateWishlistItem(wishlist, wishlistItemDTO);
    }

    private Map<String, String> updateWishlistItem(Wishlist wishlist, WishListItemDTO wishlistItemDTO) {
        Long productId = wishlistItemDTO.getId();
        boolean isAdd = wishlistItemDTO.isAdd();

        List<WishlistItem> existingWishlistItems = wishlist.getItems();
        Map<String, String> response = new HashMap<>();

        if (isAdd) {
            boolean alreadyExists = existingWishlistItems.stream()
                    .anyMatch(item -> item.getProduct().getId().equals(productId));

            if (alreadyExists) {
                response.put("message", "Product already exists in wishlist");
                return response;
            } else {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

                WishlistItem newItem = new WishlistItem(wishlist, product);
                newItem.setWishlist(wishlist);
                wishlist.getItems().add(newItem);
                wishlistRepository.save(wishlist);

                response.put("message", "Product added to wishlist");
                return response;
            }
//            if (!alreadyExists) {
//                Product product = productRepository.findById(productId)
//                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
//
//                WishlistItem newItem = new WishlistItem(wishlist, product);
//                newItem.setWishlist(wishlist); // Ensure bidirectional mapping
//                wishlist.getItems().add(newItem);
//            }
        } else {
            Iterator<WishlistItem> iterator = existingWishlistItems.iterator();
            while (iterator.hasNext()) {
                WishlistItem item = iterator.next();
                if (item.getProduct().getId().equals(productId)) {
                    iterator.remove();
                }
            }
        }

        wishlistRepository.save(wishlist);
        response.put("message", "Product removed from wishlist");
        return response;
    }

    public Wishlist getWishlistByUserId(Long userId) {
        return wishlistRepository.findByUserId(userId)
                .orElse(null);
    }


    public void deleteWishlist(Long wishlistId) {
        wishlistRepository.deleteById(wishlistId);
    }
}


