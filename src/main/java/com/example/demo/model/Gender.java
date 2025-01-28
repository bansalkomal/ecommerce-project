package com.example.demo.model;

public enum Gender {
    male,
    female;

    @Override
    public String toString() {
        switch (this) {
            case male: return "male";
            case female: return "female";
            default: throw new IllegalArgumentException("Unexpected value: " + this);
        }
    }
}
