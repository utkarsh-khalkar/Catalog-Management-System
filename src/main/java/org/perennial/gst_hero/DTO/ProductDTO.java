package org.perennial.gst_hero.DTO;


import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.Map;

/**
 * Author: Utkarsh Khalkar
 * Title: Product DTO class to handle Product Data
 * Date:  08:04:2025
 * Time:  04:10 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    @NotBlank(message = "Product name must not be blank")
    private String productName;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Category code must not be blank")
    private String categoryCode;

    @NotNull(message = "User ID must not be null")
    @Min(value = 1, message = "User ID must be greater than or equal to 1")
    private Integer userId;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    private Map<String, Object> attributes;

}

