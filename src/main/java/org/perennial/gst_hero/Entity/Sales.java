package org.perennial.gst_hero.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * Author: Utkarsh Khalkar
 * Title:  Sales Details Entity Class
 * Date:   03-04-2025
 * Time:   09:16 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales_details")
@Entity
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SALES_ID")
    private long sales_id;
    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "CREATED_AT")
    private LocalDate createdAt;

    @Column(name = "PRODUCT_PRICE")
    private double productPrice;

    @Column(name = "FINANCIAL_YEAR")
    private String financialYear;

    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "TOTAL_PRICE")
    private double totalPrice;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID",nullable = false)
    private User user;

}
