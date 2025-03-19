package com.example.demo.dto;

import lombok.Data;

@Data
public class BillingResponseDTO extends AddressDTO {
    private boolean deliveryAndBillingAddressesAreSame;
}