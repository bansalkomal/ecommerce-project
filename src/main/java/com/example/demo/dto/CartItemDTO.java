package com.example.demo.dto;

public class CartItemDTO {

    private Long id;
    private String name;
    private Integer quantity;
    private Double price;

    private boolean isAdd;

    public CartItemDTO(Long productId, String productName, Integer quantity, Double price) {
        this.id = productId;
        this.name = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isAdd() {
        return isAdd;
    }

    // Setter for isAdd
    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }
}
