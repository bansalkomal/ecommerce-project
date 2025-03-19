package com.example.demo.dto;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String email;
    private String mobileNumber;
    private BillingResponseDTO billingAddress;
    private AddressDTO shippingAddress;
}