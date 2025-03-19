package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@OneToOne
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name="coupon_discount")
    private Double couponDiscount;

    @Column(name="couponCode")
    private String couponCode;

    @Column(name="shipping_charge")
    private Double shippingCharge;

    @Column(name="final_price")
    private Double finalPrice;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItems;

    public Cart() {}

    public Cart(Long userId, Double totalPrice, Double couponDiscount,
                Double shippingCharge, Double finalPrice, String couponCode) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.couponDiscount = couponDiscount;
        this.shippingCharge = shippingCharge;
        this.finalPrice = totalPrice - couponDiscount + shippingCharge;
        this.couponCode = couponCode;
        this.cartItems = new ArrayList<>();
    }

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setCart(this);
    }

    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCart(null);
    }

    //    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
