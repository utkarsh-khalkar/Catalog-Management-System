package org.perennial.gst_hero.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Author: Utkarsh Khalkar
 * Title:  Category DTO Class to handle  category Data
 * Date:   27-03-2025
 * Time:   06:57 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @NotBlank(message = "Category Code cannot be blank")
    @Pattern(regexp = "^[A-Za-z]{4}[0-9]{4}",message = "Invalid Category Code it Start with 4 Character then 4 number")
    private String categoryCode;

    @NotBlank(message = "Category Name cannot be blank")
    private String categoryName;

    @NotBlank(message = "Category Description Cannot be blank ")
    private String description;

    @NotNull(message = "User ID Cannot be null")
    private long userID;
}
