package org.perennial.gst_hero.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Author: Utkarsh Khalkar
 * Title:  Purchase Entity Class for table creation in DB
 * Date:   07:04:2025
 * Time:   09:37 AM
 */
@Entity
@Table(name = "purchase_details")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PURCHASE_ID")
    private long purchase_id;
    @Column(name = "PRODUCT_NAME")
    private String productName;
    @Column(name = "QUANTITY")
    private int quantity;
    @Column(name = "PRICE")
    private double price;
    @Column(name = "CREATED_AT")
    private LocalDate createdAt;
    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;
    @Column(name = "FINANCIAL_YEAR")
    private String financialYear;
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @Column(name = "SELLER_NAME")
    private String sellerName;
    @Column(name = "TOTAL_PRICE")
    private double totalPrice;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID",nullable = false)
    private User user;

}
