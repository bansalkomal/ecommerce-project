package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemSpecificDTO {

    private Long count;
    private Double totalPrice;
    private Double couponDiscount;
    private Double shippingCharge;
    private Double finalPrice;
}
