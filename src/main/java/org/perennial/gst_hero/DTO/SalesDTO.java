package org.perennial.gst_hero.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.perennial.gst_hero.annotation.ValidFinancialYear;

/**
 * Author: Utkarsh Khalkar
 * Title:  Sales DTO to handle sales-related Data
 * Date:   03-04-2025
 * Time:   09:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {

    @NotBlank(message = "Category Code cannot be blank")
    @Pattern(regexp = "^[A-Za-z]{4}[0-9]{4}",message = "Invalid Category Code it Start with 4 Character then 4 number")
    private String categoryCode;

    @NotBlank(message = "Product code cannot be blank")
    private String productCode;

    @NotNull(message = "Product Price cannot be empty")
    @Positive(message = "Product price must be a positive value")
    private double productPrice;

    @NotNull(message = "Quantity Cannot be empty")
    @Positive(message = "Quantity must be a positive number")
    private int quantity;

    @NotBlank(message = "Financial year cannot be blank")
    @ValidFinancialYear
    private String financialYear;

    @Min(value = 1, message = "User ID must be at least 1")
    private long userId;
}
