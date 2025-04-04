package org.perennial.gst_hero.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.SalesDTO;
import org.perennial.gst_hero.DTO.SalesParameterDTO;
import org.perennial.gst_hero.Handler.SalesRequestHandler;
import org.perennial.gst_hero.apiresponsedto.ApiResponseDTO;
import org.perennial.gst_hero.constant.ErrorMessage;
import org.perennial.gst_hero.constant.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Author: Utkarsh Khalkar
 * Title: Sales Controller to handle request
 * Date:  03-04-2025
 * Time:  10:01 AM
 */
@Slf4j
@RestController
@RequestMapping("/catalog/sales")
public class SalesController {

    @Autowired
    private  SalesRequestHandler salesRequestHandler;

    /**
     * Handles the request to save sales details.
     * @param salesDTO The sales data transfer
     * @return A ResponseEntity containing success message and HTTP status.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<Object>> saveSalesDetails(@Valid @RequestBody SalesDTO salesDTO) {

        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: saveSalesDetails :: CATEGORY_CODE ::" + salesDTO.getCategoryCode());
        boolean isUserExists= salesRequestHandler.findUserById(salesDTO.getUserId());
        if (!isUserExists) {
            return new ResponseEntity<>(
                    salesRequestHandler.apiResponse(
                            Collections.emptyList(),
                            ErrorMessage.USER_NOT_FOUND,
                            HttpStatus.NOT_FOUND.value(),
                            ErrorMessage.ERROR_STATUS
                    ),
                    HttpStatus.NOT_FOUND);
        }

        salesRequestHandler.saveSalesDetails(salesDTO);

        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: saveSalesDetails :: CATEGORY_CODE ::" + salesDTO.getCategoryCode());

        return new ResponseEntity<>(
                salesRequestHandler.apiResponse(
                        Collections.emptyList(),
                        SuccessMessage.SALES_CREATED,
                        HttpStatus.CREATED.value(),
                        SuccessMessage.SUCCESS_STATUS
                ),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/history")
    public ResponseEntity<ApiResponseDTO<Object>> generateSalesHistory(@RequestBody SalesParameterDTO salesParameterDTO) {

        log.info("START :: CLASS :: SalesController :: METHOD :: generateSalesHistory :: USER_ID :: " + salesParameterDTO.getUserId());
        boolean isUserExists = salesRequestHandler.findUserById(salesParameterDTO.getUserId());
        if (!isUserExists) {
            return new ResponseEntity<>(
                    salesRequestHandler.apiResponse(
                            Collections.emptyList(),
                            ErrorMessage.USER_NOT_FOUND,
                            HttpStatus.NOT_FOUND.value(),
                            ErrorMessage.ERROR_STATUS
                    ),
                    HttpStatus.NOT_FOUND);

        }
        boolean isGenerated=salesRequestHandler.isReportGenerated(salesParameterDTO);
        if (isGenerated) {
            return new ResponseEntity<>(
                    salesRequestHandler.apiResponse(
                            Collections.emptyList(),
                            ErrorMessage.SALES_REPORT_ALREADY_GENERATED,
                            HttpStatus.CONFLICT.value(),
                            ErrorMessage.ERROR_STATUS
                    ),
                    HttpStatus.CONFLICT
            );
        }
        salesRequestHandler.saveSalesParameterDetails(salesParameterDTO);
        log.info("END :: CLASS :: SalesController :: METHOD :: generateSalesHistory :: USER_ID :: " + salesParameterDTO.getUserId());
        return new ResponseEntity<>(
                salesRequestHandler.apiResponse(
                        Collections.emptyList(),
                        SuccessMessage.REPORT_GENERATION_MESSAGE,
                        HttpStatus.CREATED.value(),
                        SuccessMessage.SUCCESS_STATUS
                ),
                HttpStatus.CREATED
        );

    }

}
