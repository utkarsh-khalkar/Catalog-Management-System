package org.perennial.gst_hero.service;

import org.perennial.gst_hero.Entity.SalesParameter;

import java.time.LocalDate;
import java.util.List;

/**
 * Author: Utkarsh Khalkar
 * Title:  Sales Parameter service provider
 * Date:   03-04-2025
 * Time:   12:47 PM
 */
public interface SalesParameterService {
    void saveSalesParameter(SalesParameter salesParameter);
    List<SalesParameter> findAllSalesParameterByStatus(String status);
    SalesParameter findByFinancialYear(String financialYear);
    SalesParameter findByAllParams(String financialYear, String month, LocalDate startDate, LocalDate endDate,long userId);

}
