package org.perennial.gst_hero.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.perennial.gst_hero.annotation.validator.FinancialYearValidator;

import java.lang.annotation.*;

/**
 * Author: Utkarsh Khalkar
 * Title:  Custom annotation to validate financial year
 * Date:   03-04-2025
 * Time:   11:06 AM
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {FinancialYearValidator.class})
public @interface ValidFinancialYear {
    String message() default "Invalid Financial Year ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
