package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/user/{userId}")
    public Cart getCartProducts(@PathVariable Long userId) {
//        Cart cart = cartService.getCartByUserId(userId);
//        if (cart != null) {
//            List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
//            List<CartItemDTO> cartItemDtoList = new ArrayList<>();
//            for (CartItem cartItem : cartItems) {
//                CartItemDTO cartItemDto = new CartItemDTO(cartItem.getProduct().getId(),
//                        cartItem.getQuantity(), cartItem.getProduct().getPrice());
//                cartItemDtoList.add(cartItemDto);
//            }
//
//            return cartItemDtoList;
//        }
//        return new ArrayList<>();
        return cartRepository.findByUserId(userId);
    }

    @GetMapping("/user/specific/{userId}")
    public CartItemSpecificDTO getSpecificCartProducts(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart != null) {
            List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
            long count=0;
            for (CartItem cartItem : cartItems) {
                count = count + cartItem.getQuantity();
            }
            CartItemSpecificDTO cartItemDto = new CartItemSpecificDTO(count, cart.getTotalPrice(),
                    cart.getCouponDiscount(),
                    cart.getShippingCharge(),
                   cart.getFinalPrice());
            return cartItemDto;
        }
        return new CartItemSpecificDTO();
    }

    @Transactional
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteCart(@PathVariable Long userId) {
//        try {
//            cartService.deleteCart(userId);
//            return new ResponseEntity<>("Cart and associated items deleted successfully", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Cart and associated items not found", HttpStatus.NOT_FOUND);
//        }

        ApiResponse<Void> response = new ApiResponse<>();
        try {
            cartService.deleteCart(userId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Cart and associated items deleted successfully");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Cart and associated items not found");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("/create/{userId}")
    public ResponseEntity<Cart> createCart(@PathVariable Long userId, @RequestBody List<CartItemDTO> cartItemDTO) {
        Cart cart = cartService.createCart(userId, cartItemDTO);
//        List<CartItem> cartItems = cart.getCartItems();
//        List<CartItemDTO> cartItemDtoList = new ArrayList<>();
//        for (CartItem cartItem : cartItems) {
//            CartItemDTO cartItemDto = new CartItemDTO(cartItem.getProduct().getId(),
//                    cartItem.getQuantity(), cartItem.getProduct().getPrice());
//            cartItemDtoList.add(cartItemDto);
//        }
//        return ResponseEntity.ok(cartItemDtoList);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/apply-coupon/{userId}")
    public ResponseEntity<ApplyCouponResponseDTO> applyCoupon(
            @PathVariable Long userId,
            @RequestBody CouponDTO couponDTO) {
        return ResponseEntity.ok(cartService.applyCoupon(userId, couponDTO.getCouponCode(), couponDTO.isApplied()));
    }
}
