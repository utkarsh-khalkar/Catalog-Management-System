package org.perennial.gst_hero.serviceImpl;

import lombok.extern.slf4j.Slf4j;

import org.perennial.gst_hero.Entity.SalesParameter;
import org.perennial.gst_hero.repository.SalesParameterRepository;
import org.perennial.gst_hero.service.SalesParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Utkarsh Khalkar
 * Title:  SalesParameterService Implementation
 * Date:   03-04-2025
 * Time:   12:50 PM
 */
@Slf4j
@Service
public class SalesParameterServiceImpl implements SalesParameterService {

    @Autowired
    private SalesParameterRepository salesParameterRepository;

    /**
     * Method to save all parameter
     * @param salesParameter object to save
     */
    @Override
    public void saveSalesParameter(SalesParameter salesParameter) {
        log.info("START :: CLASS :: SalesParameterServiceImpl :: METHOD :: saveSalesParameter :: REQUEST_DATE ::"
                +salesParameter.getCreatedAt());
        salesParameterRepository.save(salesParameter);
        log.info("END :: CLASS :: SalesParameterServiceImpl :: METHOD :: saveSalesParameter :: REQUEST_DATE ::"
                +salesParameter.getCreatedAt());

    }

    /**
     * Method to find all sales parameter by status
     * @param status to get all parameters
     * @return list of sales parameters
     */
    @Override
    public List<SalesParameter> findAllSalesParameterByStatus(String status) {
        log.info("START :: CLASS :: SalesParameterService :: METHOD :: findAllSalesParameterByStatus :: STATUS ::"+status);
        List<SalesParameter> salesParameterList = salesParameterRepository.findByStatus(status);
        log.info("END :: CLASS :: SalesParameterService :: METHOD :: findAllSalesParameterByStatus :: STATUS ::"+status);
        return salesParameterList;
    }

    /**
     * Method to find sales parameter by financial Year
     * @param financialYear to find purchase parameter
     * @return sales parameter
     */
    @Override
    public SalesParameter findByFinancialYear(String financialYear) {
        log.info("START :: CLASS :: findByFinancialYear :: METHOD :: findByFinancialYear :: FINANCIAL_YEAR:{} ::",
                financialYear);

        SalesParameter salesParameter = salesParameterRepository.findByFinancialYear(financialYear);
        log.info("END :: CLASS :: findByFinancialYear :: METHOD :: findByFinancialYear :: FINANCIAL_YEAR:{} ::",
                financialYear);
        return salesParameter;
    }




}
