package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "wishlist_item")//, uniqueConstraints = {@UniqueConstraint(columnNames = {"wishlist_id", "product_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wishlist_id", nullable = false)
    @JsonBackReference
    private Wishlist wishlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    @Column(nullable = false)
    private Product product;

    public WishlistItem(Wishlist wishlist, Product product){
        this.wishlist = wishlist;
        this.product = product;
    }

//    @Column(nullable = false, updatable = false)
//    private LocalDateTime addedAt = LocalDateTime.now();
}
