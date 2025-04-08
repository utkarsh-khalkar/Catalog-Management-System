package org.perennial.gst_hero.service;

import org.perennial.gst_hero.Entity.PurchaseParameter;

import java.util.List;

/**
 * Author: Utkarsh Khalkar
 * Title: Purchase Parameter service provider interface
 * Date:  07:04:2025
 * Time:  11:32
 */
public interface PurchaseParameterService {

    /**
     * Method to save purchase parameter
     * @param purchaseParameter object to save in db
     */
     void savePurchaseParameter(PurchaseParameter purchaseParameter);

    /**
     * Method to find all purchase parameter by status
     * @param status to find all  sales parameter
     * @return list of parameter
     */
     List<PurchaseParameter> findAllPurchaseParameterByStatus(String status);

    /**
     * Method to find sa
     * @param financialYear
     * @return
     */
     PurchaseParameter findByFinancialYear(String financialYear);


}
