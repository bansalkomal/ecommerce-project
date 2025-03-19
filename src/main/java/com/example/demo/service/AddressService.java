package com.example.demo.service;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.BillingRequestDTO;
import com.example.demo.dto.BillingResponseDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.BaseAddress;
import com.example.demo.model.BillingDetails;
import com.example.demo.model.ShippingDetails;
import com.example.demo.model.User;
import com.example.demo.repository.BillingDetailsRepository;
import com.example.demo.repository.ShippingDetailsRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillingDetailsRepository billingDetailsRepository;

    @Autowired
    private ShippingDetailsRepository shippingDetailsRepository;

    public UserResponseDTO getUserAddresses(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO response = new UserResponseDTO();
        response.setEmail(user.getEmail());
        response.setMobileNumber(user.getMobileNumber());

        if (user.getBillingDetails() != null) {
            BillingResponseDTO billingAddress = mapToDTO(user.getBillingDetails());
            response.setBillingAddress(billingAddress);
        }

        if (user.getShippingDetails() != null) {
            AddressDTO shippingAddress = mapToDTO(user.getShippingDetails());
            response.setShippingAddress(shippingAddress);
        }

        return response;
    }

    public void saveBillingAddress(Long userId, BillingRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if billing details exist, update if yes, else create new
        BillingDetails billing = billingDetailsRepository.findByUserId(userId)
                .orElse(new BillingDetails());

        billing.setUser(user);
        billing.setFirstName(dto.getFirstName());
        billing.setLastName(dto.getLastName());
        billing.setCompany(dto.getCompany());
        billing.setCountry(dto.getCountry());
        billing.setCity(dto.getCity());
        billing.setState(dto.getState());
        billing.setZip(dto.getZip());
        billing.setDeliveryAndBillingAddressesAreSame(dto.isDeliveryAndBillingAddressesAreSame());

        billingDetailsRepository.save(billing);

        // Same logic for shipping details
        ShippingDetails shipping = shippingDetailsRepository.findByUserId(userId)
                .orElse(new ShippingDetails());

        shipping.setUser(user);
        if(dto.isDeliveryAndBillingAddressesAreSame()){
            shipping.setFirstName(dto.getFirstName());
            shipping.setLastName(dto.getLastName());
            shipping.setCompany(dto.getCompany());
            shipping.setCountry(dto.getCountry());
            shipping.setCity(dto.getCity());
            shipping.setState(dto.getState());
            shipping.setZip(dto.getZip());
            shippingDetailsRepository.save(shipping);
        }else{
            shipping.setFirstName(dto.getShipping_firstName());
            shipping.setLastName(dto.getShipping_lastName());
            shipping.setCompany(dto.getShipping_company());
            shipping.setCountry(dto.getShipping_country());
            shipping.setCity(dto.getShipping_city());
            shipping.setState(dto.getShipping_state());
            shipping.setZip(dto.getShipping_zip());
            shippingDetailsRepository.save(shipping);
        }
    }

//    public void saveShippingAddress(Long userId, AddressDTO dto) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        ShippingDetails shipping = new ShippingDetails();
//        shipping.setUser(user);
//        shipping.setFirstName(dto.getFirstName());
//        shipping.setLastName(dto.getLastName());
//        shipping.setCompany(dto.getCompany());
//        shipping.setCountry(dto.getCountry());
//        shipping.setCity(dto.getCity());
//        shipping.setState(dto.getState());
//        shipping.setZip(dto.getZip());
//
//        shippingDetailsRepository.save(shipping);
//    }

    private AddressDTO mapToDTO(BaseAddress address) {
        AddressDTO dto = new AddressDTO();
        dto.setFirstName(address.getFirstName());
        dto.setLastName(address.getLastName());
        dto.setCompany(address.getCompany());
        dto.setCountry(address.getCountry());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZip(address.getZip());
        return dto;
    }

    private BillingResponseDTO mapToDTO(BillingDetails billingDetails) {
        BillingResponseDTO dto = new BillingResponseDTO();
        dto.setFirstName(billingDetails.getFirstName());
        dto.setLastName(billingDetails.getLastName());
        dto.setCompany(billingDetails.getCompany());
        dto.setCountry(billingDetails.getCountry());
        dto.setCity(billingDetails.getCity());
        dto.setState(billingDetails.getState());
        dto.setZip(billingDetails.getZip());
        dto.setDeliveryAndBillingAddressesAreSame(billingDetails.isDeliveryAndBillingAddressesAreSame());
        return dto;
    }
}
