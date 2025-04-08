package org.perennial.gst_hero.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.PurchaseDTO;
import org.perennial.gst_hero.DTO.PurchaseParameterDTO;
import org.perennial.gst_hero.Handler.PurchaseRequestHandler;
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
 * Title:  Purchase controller to handler purchase request
 * Date:   07:04:2025
 * Time:   10:25 AM
 */
@Slf4j
@RestController
@RequestMapping("catalog/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseRequestHandler purchaseRequestHandler;

    /**
     * Method to save purchase parameter into database
     * @param purchaseDTO object to save purchase parameter
     * @return response
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<Object>> savePurchaseDetails(@Valid @RequestBody PurchaseDTO purchaseDTO) {
        log.info("START :: CLASS :: PurchaseController :: METHOD :: savePurchaseDetails :: purchaseDTO :: "+purchaseDTO);
        boolean isUserExist= purchaseRequestHandler.findUserById(purchaseDTO.getUserId());
        if (!isUserExist) {
            ApiResponseDTO<Object> apiResponseDTO=purchaseRequestHandler.apiResponse(Collections.emptyList(),
                    ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND.value(),ErrorMessage.ERROR_STATUS);
            return new ResponseEntity<>(apiResponseDTO,HttpStatus.NOT_FOUND);
        }
        long purchaseId = purchaseRequestHandler.savePurchaseDetails(purchaseDTO);
        Map<String,Object> responseData=new HashMap<>();
        responseData.put("purchaseId",purchaseId);
        ApiResponseDTO<Object> apiResponseDTO=purchaseRequestHandler.apiResponse(responseData,
                SuccessMessage.PURCHASE_CREATED, HttpStatus.CREATED.value(), SuccessMessage.SUCCESS_STATUS);
        log.info("END :: CLASS :: PurchaseController :: METHOD :: savePurchaseDetails :: purchaseDTO :: "+purchaseDTO);
        return new ResponseEntity<>(apiResponseDTO,HttpStatus.CREATED);

    }

    /**
     * Method to generate purchase history
     * @param purchaseParameterDTO object to generate purchase history
     * @return response with status and message
     */
    @PostMapping("/history")
    public ResponseEntity<ApiResponseDTO<Object>> generatePurchaseHistory(@RequestBody PurchaseParameterDTO
                                                                                      purchaseParameterDTO) {
        log.info("START :: CLASS :: PurchaseController :: METHOD :: generatePurchaseHistory :: purchaseParameterDTO:{}::" +
                " ",purchaseParameterDTO);
        boolean isUserExist= purchaseRequestHandler.findUserById(purchaseParameterDTO.getUserId());
        if (!isUserExist) {
            ApiResponseDTO<Object> apiResponseDTO=purchaseRequestHandler.apiResponse(Collections.emptyList(),
                    ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND.value(),ErrorMessage.ERROR_STATUS);
            return new ResponseEntity<>(apiResponseDTO,HttpStatus.NOT_FOUND);
        }
        boolean isReportGenerated=purchaseRequestHandler.isReportGenerated(purchaseParameterDTO);
        if (isReportGenerated) {
            ApiResponseDTO<Object> apiResponseDTO=purchaseRequestHandler.apiResponse(Collections.emptyList(),
                    ErrorMessage.PURCHASE_REPORT_ALREADY_GENERATED,HttpStatus.CONFLICT.value(),ErrorMessage.ERROR_STATUS);
            return new ResponseEntity<>(apiResponseDTO,HttpStatus.CONFLICT);
        }
        purchaseRequestHandler.savePurchaseParameter(purchaseParameterDTO);
        ApiResponseDTO<Object> apiResponseDTO=purchaseRequestHandler.apiResponse(Collections.emptyList(),
                SuccessMessage.PURCHASE_HISTORY_GENERATION_REQUEST,HttpStatus.CREATED.value(),
                SuccessMessage.SUCCESS_STATUS);
        log.info("END :: CLASS :: PurchaseController :: METHOD :: generatePurchaseHistory :: purchaseParameterDTO:{}::" +
                " ",purchaseParameterDTO);
        return new ResponseEntity<>(apiResponseDTO,HttpStatus.CREATED);
    }

    /**
     * Method to delete purchase details by purchase date
     * @param userId to delete associated purchase details
     * @param purchaseDate to delete purchase details
     * @return response with status and message
     */
    @DeleteMapping("/{userId}/{purchaseDate}")
    public ResponseEntity<ApiResponseDTO<Object>> deletePurchaseDetails(@PathVariable long userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate purchaseDate) {
        log.info("START :: CLASS :: PurchaseController :: METHOD :: deletePurchaseDetails :: USER ID:{}",userId);
        boolean isUserExist= purchaseRequestHandler.findUserById(userId);
        if (!isUserExist) {
            ApiResponseDTO<Object> apiResponseDTO=purchaseRequestHandler.apiResponse(Collections.emptyList(),
                    ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND.value(),ErrorMessage.ERROR_STATUS);
            return new ResponseEntity<>(apiResponseDTO,HttpStatus.NOT_FOUND);
        }
        purchaseRequestHandler.deletePurchaseDetailsByPurchaseDate(purchaseDate);
        ApiResponseDTO<Object> apiResponseDTO=purchaseRequestHandler.apiResponse(Collections.emptyList(),
                SuccessMessage.PURCHASE_DETAILS_DELETED,HttpStatus.OK.value(), SuccessMessage.SUCCESS_STATUS);
        log.info("END :: CLASS :: PurchaseController :: METHOD :: deletePurchaseDetails :: USER ID:{}",userId);
        return new ResponseEntity<>(apiResponseDTO,HttpStatus.OK);
    }

    @PutMapping("/{purchaseId}")
    public ResponseEntity<ApiResponseDTO<Object>> updatePurchaseDetails(@PathVariable(name = "purchaseId") long purchaseId,
                                                                        @Valid @RequestBody
                                                                        PurchaseDTO purchaseDTO) {
        boolean isRecordExist=purchaseRequestHandler.isPurchaseRecordExist(purchaseId);
        if (!isRecordExist) {
            ApiResponseDTO<Object> apiResponseDTO=purchaseRequestHandler.apiResponse(Collections.emptyList(),
                    ErrorMessage.PURCHASE_REQUEST_NOT_FOUND,HttpStatus.NOT_FOUND.value(),ErrorMessage.ERROR_STATUS);
            return new ResponseEntity<>(apiResponseDTO,HttpStatus.NOT_FOUND);
        }
        boolean isUserExist= purchaseRequestHandler.findUserById(purchaseDTO.getUserId());
        if (!isUserExist) {
            ApiResponseDTO<Object> apiResponseDTO=purchaseRequestHandler.apiResponse(Collections.emptyList(),
                    ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND.value(),ErrorMessage.ERROR_STATUS);
            return new ResponseEntity<>(apiResponseDTO,HttpStatus.NOT_FOUND);
        }
        boolean isCurrentFinancialYear= purchaseRequestHandler.
                isCurrentFinancialYear(purchaseDTO.getFinancialYear());
        if (isCurrentFinancialYear) {
            purchaseRequestHandler.updatePurchaseDetails(purchaseId,purchaseDTO);
            ApiResponseDTO<Object> apiResponseDTO=purchaseRequestHandler.apiResponse(Collections.emptyList(),
                    SuccessMessage.PURCHASE_RECORD_UPDATED,HttpStatus.OK.value(),SuccessMessage.SUCCESS_STATUS);
            return new ResponseEntity<>(apiResponseDTO,HttpStatus.OK);
        }
        ApiResponseDTO<Object> apiResponseDTO=purchaseRequestHandler.apiResponse(Collections.emptyList(),
                ErrorMessage.PAST_FINANCIAL_REQUEST,HttpStatus.CONFLICT.value(),ErrorMessage.ERROR_STATUS);
        return new ResponseEntity<>(apiResponseDTO,HttpStatus.CONFLICT);
    }

}
