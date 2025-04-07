package org.perennial.gst_hero.service;

import org.perennial.gst_hero.Entity.Purchase;

import java.time.LocalDate;
import java.util.List;

/**
 * Author: Utkarsh Khalkar
 * Title:  Purchase Service Provider interface
 * Date:   07:03:2025
 * Time:   10:01 AM
 */
public interface PurchaseService {

    void savePurchaseDetails(Purchase purchase);
    int getCountOfPurchaseDetails(String financialYear,String month,String sellerName,long userId);
    List<Purchase> findPurchaseDetailsWithBatchSize(String financialYear,String month,String sellerName,long userId,int batchSize,int offset);
    void deletePurchaseDetailsByPurchaseDate(LocalDate date);
}
