package com.example.demo.model;
import javax.persistence.*;

@Entity
@Table(name = "billing_details")
public class BillingDetails extends BaseAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, name = "delivery_and_billing_addresses_are_same")
    private boolean deliveryAndBillingAddressesAreSame;

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

    public boolean isDeliveryAndBillingAddressesAreSame() {
        return deliveryAndBillingAddressesAreSame;
    }

    public void setDeliveryAndBillingAddressesAreSame(boolean deliveryAndBillingAddressesAreSame) {
        this.deliveryAndBillingAddressesAreSame = deliveryAndBillingAddressesAreSame;
    }
}
