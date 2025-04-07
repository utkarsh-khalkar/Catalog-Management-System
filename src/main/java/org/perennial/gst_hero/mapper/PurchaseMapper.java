package org.perennial.gst_hero.mapper;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.PurchaseDTO;
import org.perennial.gst_hero.Entity.Purchase;
import org.perennial.gst_hero.Entity.User;

import java.time.LocalDate;

/**
 * Author: Utkarsh Khalkar
 * Title:  Purchase Mapper class which map PurchaseDTO to Purchase Entity
 * Date:   07:04:2025
 * Time:   10:12 AM
 */
@Slf4j
public class PurchaseMapper {

    /**
     * Method to convert dto object to entity
     * @param purchaseDTO object to convert purchase entity
     * @param user object assign user details
     * @return purchase object
     */
    public static Purchase toPurchase(PurchaseDTO purchaseDTO, User user) {
        log.info("START :: CLASS :: PurchaseMapper :: METHOD :: toPurchase :: PURCHASEDTO ::"+purchaseDTO);
        Purchase purchase = new Purchase();
        purchase.setCategoryCode(purchaseDTO.getCategoryCode());
        purchase.setPrice(purchaseDTO.getPrice());
        purchase.setSellerName(purchaseDTO.getSellerName());
        purchase.setCreatedAt(LocalDate.now());
        purchase.setUpdatedAt(LocalDate.now());
        purchase.setQuantity(purchaseDTO.getQuantity());
        purchase.setProduct_code(purchaseDTO.getProduct_code());
        purchase.setUser(user);
        purchase.setFinancialYear(purchaseDTO.getFinancialYear());
        log.info("END :: CLASS :: PurchaseMapper :: METHOD :: toPurchase :: PURCHASEDTO ::"+purchaseDTO);
        return purchase;
    }
}
