package com.example.demo.model;
import javax.persistence.*;

@Entity
@Table(name = "shipping_details")
public class ShippingDetails extends BaseAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @Column(nullable = false, name = "ship_different_address")
//    private boolean shipDifferentAddress;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public boolean isShipDifferentAddress() {
//        return shipDifferentAddress;
//    }
//
//    public void setShipDifferentAddress(boolean shipDifferentAddress) {
//        this.shipDifferentAddress = shipDifferentAddress;
//    }
}