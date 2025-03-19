package com.example.demo.service;

import com.example.demo.Utility;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }


    public Order createOrder(Long userId, List<OrderItemDTO> orderItemsDTO) throws MessagingException {
        Order order = new Order(userId, 0.0, new Date());

        double totalAmount = 0.0;

        for (OrderItemDTO itemDTO : orderItemsDTO) {
            Product product = productRepository.findById(itemDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (product.getStock() < itemDTO.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            OrderItem orderItem = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.addOrderItem(orderItem);

            // Decrease product quantity
            product.setStock(product.getStock() - itemDTO.getQuantity());
            productRepository.save(product);

            totalAmount += product.getPrice() * itemDTO.getQuantity();
        }

        order.setTotalPrice(totalAmount);

        return orderRepository.save(order);
    }
}
