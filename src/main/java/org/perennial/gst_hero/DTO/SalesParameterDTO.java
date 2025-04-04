package org.perennial.gst_hero.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.perennial.gst_hero.annotation.ValidFinancialYear;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Author:  Utkarsh Khalkar
 * Title:   Sales Parameter DTO to handle sales parameter data
 * Date:    03:04:2025
 * Time:    12:38 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesParameterDTO {

    private String month;
    @ValidFinancialYear
    private String financialYear;
    private LocalDate startDate;
    private LocalDate endDate;
    @Min(value = 1, message = "User ID must be at least 1")
    private int userId;

}
