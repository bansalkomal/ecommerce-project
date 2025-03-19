package com.example.demo.dto;

public class ApplyCouponResponseDTO {
    private boolean success;
    private String message;

    public ApplyCouponResponseDTO(){
        this.success = false;
        this.message = "";
    }
    public ApplyCouponResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

