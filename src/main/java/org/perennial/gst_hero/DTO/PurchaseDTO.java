package org.perennial.gst_hero.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.perennial.gst_hero.annotation.ValidFinancialYear;

/**
 * Author: Utkarsh Khalkar
 * Title:  Purchase DTO class to handler purchase data
 * Date:   07:04:2025
 * Time:   09:56 AM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {

    @NotBlank(message = "Product Name cannot be blank")
    private String productName;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private double price;

    @NotBlank(message = "Financial Year cannot be blank")
    @ValidFinancialYear
    private String financialYear;

    @NotBlank(message = "Category Name cannot be blank")
    private String categoryName;

    @NotBlank(message = "Seller name cannot be blank")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Seller name must contain only letters")
    private String sellerName;

    @Min(value = 1, message = "User ID must be at least 1")
    private long userId;
}

