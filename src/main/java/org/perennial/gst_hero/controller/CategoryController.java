package org.perennial.gst_hero.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.CategoryDTO;
import org.perennial.gst_hero.Handler.CategoryRequestHandler;
import org.perennial.gst_hero.apiresponsedto.ApiResponseDTO;
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
 * Title: Category Controller to handle request
 * Date:  27-03-2025
 * Time:  7:00 AM
 */
@Slf4j
@RestController
@RequestMapping("/catalog/categories")
public class CategoryController {

    @Autowired
    private CategoryRequestHandler categoryRequestHandler;

    /**
     *
     * @param categoryDTO object to save category details in db
     * @return response category added
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<Object>> saveCategoryDetails(@Valid @RequestBody CategoryDTO categoryDTO) {
        log.info("START :: CLASS :: CategoryController METHOD :: saveCategoryDetails :: categoryCode :: " +
                categoryDTO.getCategoryCode());
        boolean isCategoryExist= categoryRequestHandler.findCategoryEntry(categoryDTO);
        boolean isUserExist= categoryRequestHandler.findUserById(categoryDTO.getUserID());
        if (!isUserExist)
        {
            ApiResponseDTO<Object> responseDTO=categoryRequestHandler.apiResponse(Collections.emptyList(),
                    "User Not Found", HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()),0);
            log.info("ERROR :: CLASS :: CategoryController METHOD :: saveCategoryDetails :: categoryCode :: "
                    + categoryDTO.getCategoryCode());
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
        if (isCategoryExist) {

            ApiResponseDTO<Object> responseDTO=categoryRequestHandler.apiResponse(Collections.emptyList(),
                    "Category Already Exist", HttpStatus.valueOf(HttpStatus.CONFLICT.value()),0);

            return new ResponseEntity<>(responseDTO, HttpStatus.CONFLICT);
        }
        log.info("END :: CLASS :: CategoryController METHOD :: saveCategoryDetails :: categoryDTO :: " +
                categoryDTO.getCategoryCode());

        categoryRequestHandler.saveCategory(categoryDTO);
        ApiResponseDTO<Object> responseDTO=categoryRequestHandler.apiResponse(Collections.emptyList(),
                "Category Created Successfully", HttpStatus.valueOf(HttpStatus.CREATED.value()),1);

        return new  ResponseEntity<>(responseDTO,HttpStatus.CREATED);
    }
}
