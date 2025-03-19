package com.example.demo.controller;

import com.example.demo.Utility;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.OrderItemResponseDTO;
import com.example.demo.dto.OrderResponseDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public List<OrderResponseDTO> getOrdersByUserId(@PathVariable Long userId) {

        List<Order> orders = orderService.getOrdersByUserId(userId);

        return mapOrdersToOrderResponseDTO(orders);
    }

    @Transactional
    @PostMapping("/create/{userId}")
    public ResponseEntity<OrderResponseDTO> createOrder(@PathVariable Long userId, @RequestBody List<OrderItemDTO> orderItemsDTO) throws MessagingException {
        List<Order> orders = new ArrayList<>();
        Order order = orderService.createOrder(userId, orderItemsDTO);
<<<<<<< Updated upstream
        String message = "Order placed successfully";
        //Utility.sendEmail(message);
        return ResponseEntity.ok(order);
=======
        orders.add(order);
        //String message = "Order placed successfully";
        OrderResponseDTO orderResponseDTO = mapOrdersToOrderResponseDTO(orders).get(0);
        Utility utility = new Utility();
        User user = userRepository.findById(userId).orElse(null);
        utility.sendOrderEmail(user.getEmail(), orderResponseDTO);
        return ResponseEntity.ok(orderResponseDTO);
    }

    List<OrderResponseDTO> mapOrdersToOrderResponseDTO(List<Order> orders) {
        return orders.stream().map(order -> {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setId(order.getId());
            dto.setOrderDate(order.getOrderDate());
            dto.setTotalPrice(order.getTotalPrice());

            List<OrderItemResponseDTO> itemDTOs = order.getOrderItems().stream().map(item -> {
                OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
                itemDTO.setId(item.getId());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setPrice(item.getPrice());

                Product product = item.getProduct();
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(product.getId());
                productDTO.setName(product.getName());
                productDTO.setDescription(product.getDescription());
                productDTO.setPrice(product.getPrice());
                productDTO.setBrand(product.getBrand());
                productDTO.setImageUrl(product.getImageUrl());
                productDTO.setColor(product.getColor());
                productDTO.setSize(product.getSize());
                productDTO.setProductCode(product.getProductCode());
                productDTO.setSubCategoryItemId(productDTO.getSubCategoryItemId());

                itemDTO.setProduct(productDTO);
                return itemDTO;
            }).collect(Collectors.toList());

            dto.setOrderItems(itemDTOs);
            return dto;
        }).collect(Collectors.toList());
>>>>>>> Stashed changes
    }
}

