package com.example.demo.dto;
import lombok.Data;

import javax.persistence.Column;

@Data
public class AddressDTO {
    private String firstName;
    private String lastName;
    private String company;
    private String country;
    private String city;
    private String state;
    private String zip;
}