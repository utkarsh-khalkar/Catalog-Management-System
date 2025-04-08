package org.perennial.gst_hero.mapper;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.PurchaseParameterDTO;
import org.perennial.gst_hero.Entity.PurchaseParameter;

import java.time.LocalDate;

/**
 * Author: Utkarsh Khalkar
 * Title:  PurchaseParameterDTO to PurchaseParameter Entity
 * Date:   07-04-2025
 * Time:   11:21 AM
 */
@Slf4j
public class PurchaseParameterMapper {

    /**
     * Method to convert parameterDTO to parameter entity
     * @param purchaseParameterDTO object to convert into parameter entity
     * @return parameter object
     */
    public static PurchaseParameter toModel(PurchaseParameterDTO purchaseParameterDTO) {
        log.info("START :: CLASS :: PurchaseParameterMapper:: METHOD :: toModel :: purchaseParameterDTO:{}",
                purchaseParameterDTO);
        PurchaseParameter purchaseParameter = new PurchaseParameter();
        purchaseParameter.setFinancialYear(purchaseParameterDTO.getFinancialYear());
        purchaseParameter.setStatus("OPEN");
        purchaseParameter.setCreatedAt(LocalDate.now());
        purchaseParameter.setUpdatedAt(LocalDate.now());
        purchaseParameter.setUserId(purchaseParameterDTO.getUserId());
        purchaseParameter.setMonth(purchaseParameterDTO.getMonth());
        purchaseParameter.setSellerName(purchaseParameterDTO.getSellerName());
        log.info("END :: CLASS :: PurchaseParameterMapper:: METHOD :: toModel :: purchaseParameterDTO:{}",
                purchaseParameterDTO);
        return purchaseParameter;
    }
}
