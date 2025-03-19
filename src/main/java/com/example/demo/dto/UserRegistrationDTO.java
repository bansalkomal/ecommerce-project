package com.example.demo.dto;

import com.example.demo.model.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class UserRegistrationDTO {
    private String username;
    private String password;
    private String email;
    private String role; // "USER" or "ADMIN"
    private String mobileNumber;
    private Gender gender;
}
