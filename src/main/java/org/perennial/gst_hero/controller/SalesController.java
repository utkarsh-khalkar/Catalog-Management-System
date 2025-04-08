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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

        log.info("START :: CLASS :: SalesRequestHandler :: METHOD :: saveSalesDetails :: SALES-DTO:{}" ,salesDTO);
        boolean isUserExists= salesRequestHandler.findUserById(salesDTO.getUserId());
        if (!isUserExists) {
            return new ResponseEntity<>(salesRequestHandler.apiResponse(Collections.emptyList(),
                    ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND.value(), ErrorMessage.ERROR_STATUS),
                    HttpStatus.NOT_FOUND);
        }
        long salesId=salesRequestHandler.saveSalesDetails(salesDTO);
        Map<String,Object> responseData=new HashMap<>();
        responseData.put("Sales ID",salesId);
        log.info("END :: CLASS :: SalesRequestHandler :: METHOD :: saveSalesDetails :: SALES-DTO:{}" ,salesDTO);

        return new ResponseEntity<>(
                salesRequestHandler.apiResponse(responseData,SuccessMessage.SALES_CREATED,
                        HttpStatus.CREATED.value(), SuccessMessage.SUCCESS_STATUS), HttpStatus.CREATED
        );
    }

    @PostMapping("/history")
    public ResponseEntity<ApiResponseDTO<Object>> generateSalesHistory(@RequestBody SalesParameterDTO salesParameterDTO)
    {

        log.info("START :: CLASS :: SalesController :: METHOD :: generateSalesHistory :: USER_ID :: " +
                salesParameterDTO.getUserId());
        boolean isUserExists = salesRequestHandler.findUserById(salesParameterDTO.getUserId());
        if (!isUserExists) {
            return new ResponseEntity<>(salesRequestHandler.apiResponse(Collections.emptyList(),
                    ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND.value(), ErrorMessage.ERROR_STATUS),
                    HttpStatus.NOT_FOUND);

        }

        salesRequestHandler.saveSalesParameterDetails(salesParameterDTO);
        log.info("END :: CLASS :: SalesController :: METHOD :: generateSalesHistory :: USER_ID :: " +
                salesParameterDTO.getUserId());
        return new ResponseEntity<>(salesRequestHandler.apiResponse(Collections.emptyList(),
                SuccessMessage.REPORT_GENERATION_MESSAGE, HttpStatus.CREATED.value(),
                SuccessMessage.SUCCESS_STATUS),HttpStatus.CREATED);

    }

    /**
     * Method to delete sales details by sales date
     * @param userId to delete associated sales record
     * @param salesDate to delete sales details
     * @return response with status and message
     */
    @DeleteMapping("/{userId}/{salesDate}")
    public ResponseEntity<ApiResponseDTO<Object>> deleteSalesDetails(@PathVariable long userId,
                                                                     @PathVariable
                                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                     LocalDate salesDate) {
        log.info("START :: CLASS :: SalesController :: METHOD :: deleteSalesDetails :: USER ID:{}",userId);
        boolean isUserExists = salesRequestHandler.findUserById(userId);
        if (!isUserExists) {
            ApiResponseDTO<Object> apiResponseDTO=salesRequestHandler.apiResponse(Collections.emptyList(),
                    ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND.value(), ErrorMessage.ERROR_STATUS);
            return new ResponseEntity<>(apiResponseDTO,HttpStatus.NOT_FOUND);
        }
        salesRequestHandler.deleteSalesDetailsBySaleDate(salesDate);
        ApiResponseDTO<Object> apiResponseDTO=salesRequestHandler.apiResponse(Collections.emptyList(),
                SuccessMessage.SALES_DETAILS_DELETED,HttpStatus.OK.value(), SuccessMessage.SUCCESS_STATUS);
        log.info("END :: CLASS :: SalesController :: METHOD :: deleteSalesDetails :: USER ID:{}",userId);
        return new ResponseEntity<>(apiResponseDTO,HttpStatus.OK);

    }

    /**
     * Method to update existing sales record
     * @param salesDTO object to update record
     * @return response with status and message
     */
    @PutMapping("/{salesId}")
    public ResponseEntity<ApiResponseDTO<Object>> updateSalesDetails(
            @PathVariable(name = "salesId") long salesId,
            @Valid @RequestBody SalesDTO salesDTO) {

        log.info("START :: CLASS :: SalesController :: METHOD :: updateSalesDetails :: SalesDetails:{}", salesDTO);
        boolean isSalesRecordExist=salesRequestHandler.isRecordExist(salesId);
        if (!isSalesRecordExist) {
            ApiResponseDTO<Object> apiResponseDTO=salesRequestHandler.apiResponse(Collections.emptyList(),
                    ErrorMessage.SALES_RECORD_NOT_FOUND,HttpStatus.NOT_FOUND.value(), ErrorMessage.ERROR_STATUS);
            return new ResponseEntity<>(apiResponseDTO,HttpStatus.NOT_FOUND);
        }
        boolean isUserExists = salesRequestHandler.findUserById(salesDTO.getUserId());
        if (!isUserExists) {
            ApiResponseDTO<Object> apiResponseDTO=salesRequestHandler.apiResponse(Collections.emptyList(),
                    ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND.value(), ErrorMessage.ERROR_STATUS);
            return new ResponseEntity<>(apiResponseDTO,HttpStatus.NOT_FOUND);
        }
        boolean isCurrentFinancialYear = salesRequestHandler.isCurrentFinancialYear(salesDTO.getFinancialYear());


        if (isCurrentFinancialYear) {
            salesRequestHandler.updateSalesDetails(salesId, salesDTO);
            ApiResponseDTO<Object> apiResponseDTO = salesRequestHandler.apiResponse(Collections.emptyList(),
                    SuccessMessage.SALES_UPDATED_SCCESSFULLY, HttpStatus.OK.value(), SuccessMessage.SUCCESS_STATUS);
            log.info("END :: CLASS :: SalesController :: METHOD :: updateSalesDetails :: SalesDetails:{}", salesDTO);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
        }

        ApiResponseDTO<Object> apiResponseDTO = salesRequestHandler.apiResponse(Collections.emptyList(),
                ErrorMessage.PAST_FINANCIAL_REQUEST, HttpStatus.CONFLICT.value(), ErrorMessage.ERROR_STATUS);
        log.info("END :: CLASS :: SalesController :: METHOD :: updateSalesDetails :: SalesDetails:{}", salesDTO);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CONFLICT);
    }


}
