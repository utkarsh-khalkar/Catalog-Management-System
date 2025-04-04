package org.perennial.gst_hero.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.annotation.ValidFinancialYear;

/**
 * Author: Utkarsh Khalkar
 * Title:  FinancialYear validator class
 * Date:   03:04:2025
 * Time:   11:11 PM
 */
@Slf4j
public class FinancialYearValidator implements ConstraintValidator<ValidFinancialYear,String> {
    @Override
    public boolean isValid(String financialYear, ConstraintValidatorContext constraintValidatorContext) {
        log.info("START :: CLASS :: FinancialYearValidator :: METHOD :: isValid :: financialYear :: " + financialYear);
        if (financialYear == null || !financialYear.matches("^(20\\d{2})-(20\\d{2})$")) {
            return false;
        }
        String[] years = financialYear.split("-");
        int startYear = Integer.parseInt(years[0]);
        int endYear = Integer.parseInt(years[1]);
        log.info("END :: CLASS :: FinancialYearValidator :: METHOD :: isValid :: financialYear :: " + financialYear);
        return endYear == startYear + 1;
    }
}
