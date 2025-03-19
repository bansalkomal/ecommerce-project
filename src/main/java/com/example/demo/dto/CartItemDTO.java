package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemDTO {

    private Long id;
    //    private String name;
    private Integer quantity;
//    private Double price;
    @JsonProperty("isAdd")
    private boolean isAdd;

    public CartItemDTO(Long productId, Integer quantity, boolean isAdd) {
        this.id = productId;
//        this.name = productName;
        this.quantity = quantity;
//        this.price = price;
        this.isAdd = isAdd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }
}
