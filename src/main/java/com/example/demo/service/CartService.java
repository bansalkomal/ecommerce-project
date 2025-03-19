package com.example.demo.service;

import com.example.demo.dto.ApplyCouponResponseDTO;
import com.example.demo.dto.CartItemDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CouponRepository couponRepository;

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

//    public List<CartItemDTO> getCartItems(Long userId) {
//        Cart cart = getCartByUserId(userId);
//
//        if (cart == null) {
//            throw new RuntimeException("Cart not found for user with ID: " + userId);
//        }
//
//        return cart.getCartItems().stream()
//                .map(item -> new CartItemDTO(
//                        item.getProduct().getId(),
//                        item.getQuantity()
//                ))
//                .collect(Collectors.toList());
//    }

    public Cart createCart(Long userId, List<CartItemDTO> cartItemsDTO) {
//        Optional<User> user = userRepository.findById(userId);

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart(userId, 0.0,0.0,0.0,0.0, null);
            double totalCartPrice = 0;
            for (CartItemDTO itemDTO : cartItemsDTO) {
                Product product = productRepository.findById(itemDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                CartItem item = new CartItem(cart, product, itemDTO.getQuantity());
                //item.setCart(cart);  // Associate with the cart
                cart.addCartItem(item);
                totalCartPrice += product.getPrice() * itemDTO.getQuantity();
            }

            cart.setTotalPrice(totalCartPrice);
            cart.setFinalPrice(totalCartPrice - cart.getCouponDiscount() + cart.getShippingCharge());
            cartRepository.save(cart);
        } else {
            cart = updateCartProducts(userId, cartItemsDTO);
        }
        return cart;
    }

    public Cart updateCartProducts(Long userId, List<CartItemDTO> cartItemsDTO) {
        Cart cart = cartRepository.findByUserId(userId);
        double totalPrice = 0.0;

        for (CartItemDTO cartItemDTO : cartItemsDTO) {
            Optional<CartItem> existingItemOpt = cartItemRepository
                    .findByCartIdAndProductId(cart.getId(), cartItemDTO.getId());
            if (existingItemOpt.isPresent()) {
                CartItem existingItem = existingItemOpt.get();
                if (cartItemDTO.getQuantity() > 0) {
                    if (cartItemDTO.isAdd()) {
                        existingItem.setQuantity(existingItem.getQuantity() + 1);
                        //cartItemRepository.save(existingItem);
                    } else {
                        existingItem.setQuantity(existingItem.getQuantity() - 1);
                        //cartItemRepository.save(existingItem);
                        if (existingItem.getQuantity() == 0) {
                            cart.removeCartItem(existingItem); // Remove from the cart
                            cartItemRepository.delete(existingItem);
                            continue;
                        }
                    }
                    cartItemRepository.save(existingItem);
                } else if (cartItemDTO.getQuantity() == 0) {
                    cart.removeCartItem(existingItem); // Remove from the cart
                    cartItemRepository.delete(existingItem);
                }
            } else if (cartItemDTO.getQuantity() > 0) {
                Product product = productRepository.findById(cartItemDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                CartItem newItem = new CartItem(cart, product, cartItemDTO.getQuantity());

//                newItem.setCart(cart);
                cart.addCartItem(newItem);
                cartItemRepository.save(newItem);
            }
        }

//        List<CartItem> updatedCartItems = cartItemRepository.findByCartId(cart.getId());
//        for (CartItem item : updatedCartItems) {
//            totalPrice += item.getProduct().getPrice() * item.getQuantity(); // Assuming CartItem has a Product reference
//        }

        for (CartItem item : cart.getCartItems()) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }

        cart.setTotalPrice(totalPrice);
        cart.setFinalPrice(totalPrice - cart.getCouponDiscount() + cart.getShippingCharge());
//        cart.setCartItems(updatedCartItems);
        cartRepository.save(cart);

        return cart;
    }

    public void deleteCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cartRepository.delete(cart);
        }
    }

    public ApplyCouponResponseDTO applyCoupon(Long userId, String couponCode, boolean isApplied) {
       ApplyCouponResponseDTO applyCouponResponseDTO = new ApplyCouponResponseDTO();
        Cart cart = cartRepository.findByUserId(userId);
        //.orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        Optional<Coupon> coupon = couponRepository.findByCouponCode(couponCode);
        //.orElseThrow(() -> new ResourceNotFoundException("Invalid coupon code"));

        if (coupon.isPresent() && isApplied) {
            Double discount = coupon.get().getCouponAmount();
            cart.setCouponDiscount(discount);
            cart.setFinalPrice(cart.getTotalPrice() - coupon.get().getCouponAmount());
            cart.setCouponCode(couponCode);
            cartRepository.save(cart);
            applyCouponResponseDTO.setSuccess(true);
            applyCouponResponseDTO.setMessage("Coupon applied successfully");
            return applyCouponResponseDTO;
        } else if (coupon.isPresent() && !isApplied) {
            cart.setCouponDiscount(0.0);
            cart.setFinalPrice(cart.getTotalPrice());
            cart.setCouponCode(null);
            cartRepository.save(cart);
            applyCouponResponseDTO.setSuccess(true);
            applyCouponResponseDTO.setMessage("Coupon removed successfully");
            return applyCouponResponseDTO;
        } else {
            applyCouponResponseDTO.setSuccess(false);
            applyCouponResponseDTO.setMessage("Invalid coupon code");
            return applyCouponResponseDTO;
        }
    }
}

