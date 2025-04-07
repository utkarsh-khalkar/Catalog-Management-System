package org.perennial.gst_hero.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.Entity.PurchaseParameter;
import org.perennial.gst_hero.repository.PurchaseParameterRepository;
import org.perennial.gst_hero.service.PurchaseParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Utkarsh Khalkar
 * Title:  Purchase Parameter Service Implementation class
 * Date:   07:04:2025
 * Time:   11:34
 */
@Slf4j
@Service
public class PurchaseParameterServiceImpl implements PurchaseParameterService {

    @Autowired
    private PurchaseParameterRepository purchaseParameterRepository;
    /**
     * Method to save purchase parameters in db
     * @param purchaseParameter object to save in db
     */
    @Override
    public void savePurchaseParameter(PurchaseParameter purchaseParameter) {
        log.info("START :: CLASS :: PurchaseParameterServiceImpl :: METHOD :: savePurchaseParameter :: purchaseParameter:{}", purchaseParameter);
        purchaseParameterRepository.save(purchaseParameter);
        log.info("END :: CLASS :: PurchaseParameterServiceImpl :: METHOD :: savePurchaseParameter :: purchaseParameter:{}", purchaseParameter);
    }

    /**
     * Method to find list of purchase parameter
     * @param status to find all  purchase parameter
     * @return list of purchase parameter
     */
    @Override
    public List<PurchaseParameter> findAllPurchaseParameterByStatus(String status) {
        log.info("START :: CLASS :: PurchaseParameterServiceImpl :: METHOD :: findAllPurchaseParameterByStatus :: status:{}", status);
        List<PurchaseParameter> purchaseParameterList = purchaseParameterRepository.findByStatus(status);
        log.info("END :: CLASS :: PurchaseParameterServiceImpl :: METHOD :: findAllPurchaseParameterByStatus :: status:{}", status);
        return purchaseParameterList;
    }
}
