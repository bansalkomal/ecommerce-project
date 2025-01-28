package com.example.demo.service;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public List<CartItemDTO> getCartItems(Long userId) {
        Cart cart = getCartByUserId(userId);

        if (cart == null) {
            throw new RuntimeException("Cart not found for user with ID: " + userId);
        }

        return cart.getCartItems().stream()
                .map(item -> new CartItemDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice()
                ))
                .collect(Collectors.toList());
    }

    public Cart createCart(Long userId, List<CartItemDTO> cartItemsDTO) {
        Optional<User> user = userRepository.findById(userId);

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart(user.get(), 0.0);
            double totalCartPrice = 0;
            for (CartItemDTO itemDTO : cartItemsDTO) {

                Product product = productRepository.findById(itemDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                CartItem item = new CartItem(cart, product, product.getName(), itemDTO.getQuantity(), product.getPrice());

                item.setCart(cart);  // Associate with the cart
                cart.addCartItem(item);
                totalCartPrice += product.getPrice() * itemDTO.getQuantity();
            }

            cart.setTotalPrice(totalCartPrice);
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
                        existingItem.setQuantity(cartItemDTO.getQuantity() + 1);
                        cartItemRepository.save(existingItem);
                    } else {
                        existingItem.setQuantity(cartItemDTO.getQuantity() - 1);
                        cartItemRepository.save(existingItem);
                        if (existingItem.getQuantity() == 0) {
                            cartItemRepository.delete(existingItem);
                        }
                    }
                } else if (cartItemDTO.getQuantity() == 0) {
                    cartItemRepository.delete(existingItem);
                }
            } else if (cartItemDTO.getQuantity() > 0) {
                Product product = productRepository.findById(cartItemDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                CartItem newItem = new CartItem(cart, product, product.getName(), cartItemDTO.getQuantity(), product.getPrice());

                newItem.setCart(cart);
                cart.addCartItem(newItem);
                cartItemRepository.save(newItem);
            }
        }

        List<CartItem> updatedCartItems = cartItemRepository.findByCartId(cart.getId());
        for (CartItem item : updatedCartItems) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity(); // Assuming CartItem has a Product reference
        }

        cart.setTotalPrice(totalPrice);
        cart.setCartItems(updatedCartItems);
        cartRepository.save(cart);

        return cart;
    }

    public void deleteCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cartRepository.delete(cart);
        }
    }
}

