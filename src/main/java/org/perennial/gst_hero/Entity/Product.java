package org.perennial.gst_hero.Entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Map;

/**
 * Author: Utkarsh Khalkar
 * Title: Product Collection to create collection in Mongo
 * Date:  08-04-2025
 * Time:  04:00 PM
 */

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;

    private String productName;
    private String description;
    private Double price;
    private String categoryCode;
    private Integer userId;
    private Integer quantity;
    private LocalDate createdAt;
    private Map<String, Object> attributes;
}
