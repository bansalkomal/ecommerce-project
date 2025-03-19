package com.example.demo.dto;

import lombok.Data;

@Data
public class BillingRequestDTO extends AddressDTO{
    private boolean deliveryAndBillingAddressesAreSame;

    private String shipping_firstName;
    private String shipping_lastName;
    private String shipping_company;
    private String shipping_country;
    private String shipping_city;
    private String shipping_state;
    private String shipping_zip;

}
