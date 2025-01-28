package com.example.demo.controller;

import com.example.demo.Utility;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @Transactional
    @PostMapping("/create/{userId}")
    public ResponseEntity<Order> createOrder(@PathVariable Long userId, @RequestBody List<OrderItemDTO> orderItemsDTO) throws MessagingException {
        Order order = orderService.createOrder(userId, orderItemsDTO);
        String message = "Order placed successfully";
        //Utility.sendEmail(message);
        return ResponseEntity.ok(order);
    }
}

