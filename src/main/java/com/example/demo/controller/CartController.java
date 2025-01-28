package com.example.demo.controller;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.repository.CartItemRepository;
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

    @GetMapping("/user/{userId}")
    public List<CartItemDTO> getCartProducts(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart != null) {
            List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
            List<CartItemDTO> cartItemDtoList = new ArrayList<>();
            for (CartItem cartItem : cartItems) {
                CartItemDTO cartItemDto = new CartItemDTO(cartItem.getProduct().getId(), cartItem.getProduct().getName(),
                        cartItem.getQuantity(), cartItem.getProduct().getPrice());
                cartItemDtoList.add(cartItemDto);
            }

            return cartItemDtoList;
        }
        return new ArrayList<>();
    }

    @Transactional
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long userId) {
        try {
            cartService.deleteCart(userId);
            return new ResponseEntity<>("Cart and associated items deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cart and associated items not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("/create/{userId}")
    public ResponseEntity<List<CartItemDTO>> createCart(@PathVariable Long userId, @RequestBody List<CartItemDTO> cartItemDTO) {
        Cart cart = cartService.createCart(userId, cartItemDTO);
        List<CartItem> cartItems = cart.getCartItems();
        List<CartItemDTO> cartItemDtoList = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            CartItemDTO cartItemDto = new CartItemDTO(cartItem.getProduct().getId(), cartItem.getProduct().getName(),
                    cartItem.getQuantity(), cartItem.getProduct().getPrice());
            cartItemDtoList.add(cartItemDto);
        }
        return ResponseEntity.ok(cartItemDtoList);
    }
}
