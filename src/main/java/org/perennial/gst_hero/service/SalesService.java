package org.perennial.gst_hero.service;

import org.perennial.gst_hero.Entity.Sales;

import java.time.LocalDate;
import java.util.List;

/**
 * Author: Utkarsh Khalkar
 * Title:  Sales Service provider
 * Date:   03-04-2025
 * Time:   09:47 AM
 */
public interface SalesService {

    Sales saveSalesDetails(Sales sales);
    List<Sales> findSalesByFinancialYear(String financialYear);
    int getCountByFinancialYear(String financialYear);
    List<Sales> findSalesByFinancialYearWithBatchSize(String financialYear, int batchSize, int offset);
    List<Sales> findSalesDetailsWithBatchSize(String financialYear, LocalDate startDate,LocalDate endDate,
                                              String month,long userId,int batchSize, int offset);
    int getCountOfSalesRecord(String financialYear,LocalDate startDate,LocalDate endDate, String month,long userId);
    void deleteBySaleDate(LocalDate date);
    Sales findBySalesId(long salesId);

}
