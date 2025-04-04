package org.perennial.gst_hero.mapper;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.SalesDTO;
import org.perennial.gst_hero.Entity.Sales;
import org.perennial.gst_hero.Entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        log.info("START :: CLASS :: SalesMapper :: toModel :: salesDTO ::"+salesDTO.getCategoryCode());
        Sales sales = new Sales();
        sales.setCategoryCode(salesDTO.getCategoryCode());
        sales.setProductCode(salesDTO.getProductCode());
        sales.setQuantity(salesDTO.getQuantity());
        sales.setFinancialYear(salesDTO.getFinancialYear());
        sales.setCreatedAt(LocalDate.now());
        sales.setUpdatedAt(LocalDate.now());
        sales.setProductPrice(salesDTO.getProductPrice());
        sales.setUser(user);
        log.info("END :: CLASS :: SalesMapper :: toModel :: salesDTO ::"+salesDTO.getCategoryCode());
        return sales;

    }
}
