package com.example.demo.service;


import com.example.demo.dto.UserRegistrationDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(UserRegistrationDTO dto) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        if(dto.getRole() == null) {
            user.setRole("USER");
        } else {
            user.setRole(dto.getRole());
        }
        user.setMobileNumber(dto.getMobileNumber());
        user.setGender(dto.getGender());
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // Successful login
        }
        return null; // Invalid credentials
    }

    public User getUserProfile(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateUserProfile(User user) {
        return userRepository.save(user);
    }
}

