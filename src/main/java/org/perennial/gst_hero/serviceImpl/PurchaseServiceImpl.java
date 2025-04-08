package org.perennial.gst_hero.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.Entity.Purchase;
import org.perennial.gst_hero.repository.PurchaseRepository;
import org.perennial.gst_hero.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Author: Utkarsh Khalkar
 * Title:  Purchase Service Implementation Class
 * Date:   07:04:2025
 * Time:   10:02 AM
 */
@Slf4j
@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    /**
     * Method to Save purchase details
     * @param purchase object to save purchase details in db
     */
    @Override
    public Purchase savePurchaseDetails(Purchase purchase) {
        log.info("START :: CLASS :: PurchaseServiceImpl :: METHOD :: savePurchaseDetails :: SELLER NAME ::"+purchase.getSellerName());
        Purchase purchase1=purchaseRepository.save(purchase);
        log.info("END :: CLASS :: PurchaseServiceImpl :: METHOD :: savePurchaseDetails :: SELLER NAME ::"+purchase.getSellerName());
        return purchase1;
    }

    /**
     * Method to return count of purchase record
     * @param financialYear to count purchase record
     * @param month to count purchase record
     * @param sellerName to count purchase record
     * @param userId to count purchase record
     * @return count of purchase details
     */
    @Override
    public int getCountOfPurchaseDetails(String financialYear, String month, String sellerName, long userId) {
        log.info("START :: CLASS :: PurchaseServiceImpl :: METHOD :: getCountOfPurchaseDetails :: User ID::"+userId);
        int countOfPurchaseRecord=purchaseRepository.getCountOfPurchaseDetails(financialYear,month,sellerName,userId);
        log.info("END :: CLASS :: PurchaseServiceImpl :: METHOD :: getCountOfPurchaseDetails :: User ID::"+userId);
        return countOfPurchaseRecord;
    }

    /**
     * Method to find Purchase Details
     * @param financialYear to find purchase details
     * @param month to find purchase details
     * @param sellerName to find purchase details
     * @param userId to find purchase details
     * @param batchSize to find batch of data from purchase_details
     * @param offset skip first extracted details
     * @return list of purchase details
     */
    @Override
    public List<Purchase> findPurchaseDetailsWithBatchSize(String financialYear, String month, String sellerName,
                                                           long userId, int batchSize, int offset) {
        log.info("START :: CLASS :: PurchaseServiceImpl :: METHOD :: findPurchaseDetailsWithBatchSize :: " +
                "USER ID:{}",userId);
        List<Purchase> purchases=purchaseRepository.findPurchaseDetailsWithBatchSize(financialYear,month,
                sellerName,userId,batchSize,offset);
        log.info("END :: CLASS :: PurchaseServiceImpl :: METHOD :: findPurchaseDetailsWithBatchSize :: USER ID:{}",userId);
        return purchases;
    }

    /**
     * Method to delete purchase details by purchase date
     * @param date to delete purchase details
     */
    @Override
    public void deletePurchaseDetailsByPurchaseDate(LocalDate date) {
        log.info("START :: CLASS :: PurchaseServiceImpl :: METHOD :: deletePurchaseDetailsByPurchaseDate :: Date:{}",date);
        purchaseRepository.deleteByCreatedAt(date);
        log.info("END :: CLASS :: PurchaseServiceImpl :: METHOD :: deletePurchaseDetailsByPurchaseDate :: Date:{}",date);
    }

    /**
     * Method to find purchase record
     * @param purchaseId to find purchase record
     * @return purchase record
     */
    @Override
    public Purchase findByPurchaseId(long purchaseId) {
        log.info("START :: CLASS :: PurchaseServiceImpl :: METHOD :: findByPurchaseId :: Purchase ID:{}",purchaseId);
        Purchase purchase=purchaseRepository.findByPurchaseId(purchaseId);
        log.info("END :: CLASS :: PurchaseServiceImpl :: METHOD :: findByPurchaseId :: Purchase ID:{}",purchaseId);
        return purchase;
    }
}
