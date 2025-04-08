package org.perennial.gst_hero.mapper;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.SalesDTO;
import org.perennial.gst_hero.Entity.Sales;
import org.perennial.gst_hero.Entity.User;

import java.time.LocalDate;


/**
 * Author:  Utkarsh Khalkar
 * Title:   SalesDTO to Entity Mapper class
 * Date:    03-04-2025
 * Time:    9:36 AM
 */
@Slf4j
public class SalesMapper {

    /**
     *
     * @param salesDTO to convert into model
     * @param user object to mapped to sales
     * @return sales model
     */
    public static Sales toModel(SalesDTO salesDTO, User user) {
        log.info("START :: CLASS :: SalesMapper :: toModel :: salesDTO:{}",salesDTO);
        Sales sales = new Sales();
        sales.setCategoryName(salesDTO.getCategoryName());
        sales.setProductName(salesDTO.getProductName());
        sales.setQuantity(salesDTO.getQuantity());
        sales.setFinancialYear(salesDTO.getFinancialYear());
        sales.setCreatedAt(LocalDate.now());
        sales.setUpdatedAt(LocalDate.now());
        sales.setProductPrice(salesDTO.getProductPrice());
        double totalPrice=salesDTO.getProductPrice()*salesDTO.getQuantity();
        sales.setTotalPrice(totalPrice);
        sales.setUser(user);
        log.info("END :: CLASS :: SalesMapper :: toModel :: salesDTO:{}",salesDTO);
        return sales;

    }
}
