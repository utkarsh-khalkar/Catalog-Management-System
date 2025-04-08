package org.perennial.gst_hero.controller;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.ProductDTO;
import org.perennial.gst_hero.Handler.ProductRequestHandler;
import org.perennial.gst_hero.apiresponsedto.ApiResponseDTO;
import org.perennial.gst_hero.constant.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

/**
 * Author: Utkarsh Khalkar
 * Title:  Product Controller to handler request
 * Date:   08:04:2025
 * Time:   05:00 PM
 */

@Slf4j
@RestController
@RequestMapping("/catalog/product")
public class ProductController {

    @Autowired
    private ProductRequestHandler productRequestHandler;

    /**
     * API to save product details
     * @param productDTO object to save product details
     * @return response with status and message
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<Object>> saveProductDetails(@RequestBody  ProductDTO productDTO) {
        log.info("START :: CLASS :: ProductRequestHandler :: METHOD :: saveProductDetails :: ProductDTO:{}",productDTO);
        productRequestHandler.saveProductDetails(productDTO);
        log.info("END :: CLASS :: ProductRequestHandler :: METHOD :: saveProductDetails :: ProductDTO:{}",productDTO);
        ApiResponseDTO<Object> apiResponseDTO=productRequestHandler.apiResponse(Collections.emptyList(),
                SuccessMessage.PRODUCT_ADDED_SUCCESSFULLY, HttpStatus.CREATED.value(), SuccessMessage.SUCCESS_STATUS);
        return new ResponseEntity<>(apiResponseDTO,HttpStatus.CREATED);
    }
}
