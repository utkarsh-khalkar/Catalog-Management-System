package org.perennial.gst_hero.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.perennial.gst_hero.annotation.ValidFinancialYear;

/**
 * Author: Utkarsh Khalkar
 * Title:  Purchase Parameter DTO to handle parameter data
 * Date:   07:04:2025
 * Time:   11:17 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseParameterDTO {

    @Pattern(regexp = "^[A-Za-z]+$", message = "Seller name must contain only letters")
    private String sellerName;
    @ValidFinancialYear
    private String financialYear;
    @Min(value = 1,message = "Month Must be greater than 0")
    @Max(value = 12,message = "Month must be less than equal to 12")
    private String month;
    @Min(value = 1, message = "User ID must be at least 1")
    private int userId;
}
