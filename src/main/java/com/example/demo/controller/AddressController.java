package com.example.demo.controller;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.AddressResponseDTO;
import com.example.demo.dto.BillingRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserAddresses(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getUserAddresses(userId));
    }

    @PostMapping("/{userId}/billing")
    public ResponseEntity<AddressResponseDTO> saveBillingAddress(@PathVariable Long userId, @RequestBody BillingRequestDTO billingRequestDTO) {
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        addressService.saveBillingAddress(userId, billingRequestDTO);
        addressResponseDTO.setMessage("Address saved successfully.");
        return ResponseEntity.ok(addressResponseDTO);
    }

//    @PostMapping("/{userId}/shipping")
//    public ResponseEntity<String> saveShippingAddress(@PathVariable Long userId, @RequestBody AddressDTO addressDTO) {
//        addressService.saveShippingAddress(userId, addressDTO);
//        return ResponseEntity.ok("Shipping address saved successfully.");
//    }
}
