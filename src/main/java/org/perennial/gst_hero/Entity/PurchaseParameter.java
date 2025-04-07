package org.perennial.gst_hero.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Author: Utkarsh Khalkar
 * Title:  Purchase Parameter entity class to create table in db
 * Date:   07:02:2025
 * Time:   11:15 AM
 */
@Data
@Entity
@Table(name = "purchase_parameter")
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQUEST_ID")
    private long request_id;
    @Column(name = "FINANCIAL_YEAR")
    private String financialYear;
    @Column(name = "MONTH")
    private String month;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CREATED_AT")
    private LocalDate createdAt;
    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;
    @Column(name = "SELLER_NAME")
    private String sellerName;
    @Column(name = "USER_ID")
    private long userId;
}
