package org.perennial.gst_hero.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.Entity.Sales;
import org.perennial.gst_hero.repository.SalesRepository;
import org.perennial.gst_hero.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Author: Utkarsh Khalkar
 * Title:  Sales Service Implementation Class
 * Date:   03-04-2025
 * Time:   09-49
 */
@Slf4j
@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;
    /**
     * Method to save sales data
     * @param sales object to save in DB
     */
    @Override
    public void save(Sales sales) {
        log.info("START :: CLASS :: SalesServiceImpl :: METHOD :: save :: CATEGORY_CODE:: "+sales.getCategoryCode());
        salesRepository.save(sales);
        log.info("END :: CLASS :: SalesServiceImpl :: METHOD :: save :: CATEGORY_CODE:: "+sales.getCategoryCode());

    }
    /**
     * Method to find all sales details by financialYear
     * @param financialYear to sales  details
     * @return list of sales details
     */
    @Override
    public List<Sales> findSalesByFinancialYear(String financialYear) {
        log.info("START :: CLASS :: SalesServiceImpl :: METHOD :: findSalesByFinancialYear :: FINANCIAL_YEAR :: "+financialYear);
        List<Sales> salesList = salesRepository.findByFinancialYear(financialYear);
        log.info("END :: CLASS :: SalesServiceImpl :: METHOD :: findSalesByFinancialYear :: FINANCIAL_YEAR :: "+financialYear);
        return salesList;
    }

    /**
     * Method to get Count of sales record by financialYear
     * @param financialYear to count sales record
     * @return count of sales record
     */
    @Override
    public int getCountByFinancialYear(String financialYear) {
        log.info("START :: CLASS :: SalesServiceImpl :: METHOD :: getCountByFinancialYear :: FINANCIAL_YEAR :: "+financialYear);
        int count = salesRepository.countByFinancialYear(financialYear);
        log.info("END :: CLASS :: SalesServiceImpl :: METHOD :: getCountByFinancialYear :: FINANCIAL_YEAR :: "+financialYear);
        return count;
    }

    /**
     * Method to find sales details by batch wise
     * @param financialYear filter to find sales details
     * @param batchSize amount of data to fetch
     * @param offset skip first record
     * @return  list of sales record
     */
    @Override
    public List<Sales> findSalesByFinancialYearWithBatchSize(String financialYear, int batchSize, int offset) {
        log.info("START :: CLASS :: SalesServiceImpl :: METHOD :: findByFinancialYearWithBatchSize :: FINANCIAL_YEAR :: "+financialYear);
        List<Sales> salesList = salesRepository.findSalesByFinancialYearWithBatchSize(financialYear,batchSize,offset);
        log.info("END :: CLASS :: SalesServiceImpl :: METHOD :: findByFinancialYearWithBatchSize :: FINANCIAL_YEAR :: "+financialYear);
        return salesList;
    }

    /**
     * Method to find all sales details with optional parameter
     * @param financialYear to find sales
     * @param startDate starting date to find sales details
     * @param endDate end date to find sales details
     * @param month month to find sales details
     * @param userId to find sales details
     * @param batchSize amount of data to be fetch
     * @param offset skip specified number
     * @return list of sales details
     */
    @Override
    public List<Sales> findSalesDetailsWithBatchSize(String financialYear, LocalDate startDate, LocalDate endDate, String month, long userId, int batchSize, int offset) {
        log.info("START :: CLASS :: SalesServiceImpl :: METHOD :: findSalesDetailsWithBatchSize :: Batch Size ::"+batchSize);
        List<Sales> salesList=salesRepository.findSalesDetailsWithBatchSize(financialYear,startDate,endDate,month,userId,batchSize,offset);
        log.info("END :: CLASS :: SalesServiceImpl :: METHOD :: findSalesDetailsWithBatchSize :: Batch Size ::"+batchSize);
        return salesList;
    }

    /**
     * Method to count sales record by parameter
     * @param financialYear to count sales record
     * @param startDate to count record
     * @param endDate to count record
     * @param month specific month to count record
     * @param userId to count sales record by user
     * @return count of sales record
     */
    @Override
    public int getCountOfSalesRecord(String financialYear, LocalDate startDate, LocalDate endDate, String month, long userId) {
        log.info("START :: CLASS :: SalesServiceImpl :: METHOD :: getCountOfSalesRecord :: FINANCIAL_YEAR :: "+financialYear);
        int countOfSalesRecord=salesRepository.getCountOfSalesRecord(financialYear,startDate,endDate,month,userId);
        log.info("END :: CLASS :: SalesServiceImpl :: METHOD :: getCountOfSalesRecord :: FINANCIAL_YEAR :: "+financialYear);
        return countOfSalesRecord;
    }

    /**
     * Method to delete sales details by sale date
     * @param date to delete sales details
     */
    @Override
    public void deleteBySaleDate(LocalDate date) {
        log.info("START :: CLASS :: SalesServiceImpl :: METHOD :: deleteBySaleDate :: Date:{}",date);
        salesRepository.deleteByCreatedAt(date);
        log.info("END :: CLASS :: SalesServiceImpl :: METHOD :: deleteBySaleDate :: Date:{}",date);
    }
}
