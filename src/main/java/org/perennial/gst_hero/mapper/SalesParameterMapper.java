package org.perennial.gst_hero.mapper;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.SalesParameterDTO;
import org.perennial.gst_hero.Entity.SalesParameter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Author: Utkarsh Khalkar
 * Title:  SalesParameterDTO to SalesParameterEntity
 * Date:   03-04-2025
 * Time:   12:56 PM
 */
@Slf4j
public class SalesParameterMapper {
    public static SalesParameter toModel(SalesParameterDTO salesParameterDTO) {
        log.info("START :: CLASS :: SalesParameterMapper :: METHOD :: toModel :: USER_ID :: " + salesParameterDTO.getUserId());
        SalesParameter salesParameter = new SalesParameter();
        salesParameter.setCreatedAt(LocalDate.now());
        salesParameter.setUpdatedAt(LocalDate.now());
        salesParameter.setMonth(salesParameterDTO.getMonth());
        salesParameter.setStatus("OPEN");
        salesParameter.setUserId(salesParameterDTO.getUserId());
        salesParameter.setFinancialYear(salesParameterDTO.getFinancialYear());
        salesParameter.setStartDate(salesParameterDTO.getStartDate());
        salesParameter.setEndDate(salesParameterDTO.getEndDate());
        log.info("END :: CLASS :: SalesParameterMapper :: METHOD :: toModel :: USER_ID :: " + salesParameterDTO.getUserId());
        return salesParameter;
    }
}
