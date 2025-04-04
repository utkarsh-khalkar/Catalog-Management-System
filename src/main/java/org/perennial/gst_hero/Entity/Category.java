package org.perennial.gst_hero.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Author: Utkarsh Khalkar
 * Title:  Category Entity Class for Table Creation in DB
 * Date: 27:03:2025
 * Time  03:34 PM
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category_details")
public class Category {

    @Id
    @Column(name = "CATEGORY_CODE")
    private String categoryCode;
    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "CATEGORY_DESCRIPTION")
    private String categoryDescription;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID",nullable = false)
    private User user;
}
