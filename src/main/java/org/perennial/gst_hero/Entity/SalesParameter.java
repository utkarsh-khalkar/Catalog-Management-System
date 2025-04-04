package org.perennial.gst_hero.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;



/**
 * Author: Utkarsh Khalkar
 * Title:  Sales Parameter Entity Class
 * Date:   03-04-2025
 * Time:   12:06 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales_parameter")
public class SalesParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="REQUEST_ID")
    private long id;

    @Column(name = "MONTH" )
    private String month;

    @Column(name = "FINANCIAL_YEAR")
    private String financialYear;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "CREATED_AT")
    private LocalDate createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;

    @Column(name="STATUS")
    private String status;

    @Column(name = "USER_ID")
    private int userId;


}
