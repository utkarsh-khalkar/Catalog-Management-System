package org.perennial.gst_hero.Handler;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.ProductDTO;
import org.perennial.gst_hero.Entity.Product;
import org.perennial.gst_hero.apiresponsedto.ApiResponseDTO;
import org.perennial.gst_hero.mapper.ProductMapper;
import org.perennial.gst_hero.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: Ukarsh Khalkar
 * Title:  Product request handler class
 * Date:   08:04:2025
 * Time:   04:54 PM
 */
@Slf4j
@Component
public class ProductRequestHandler {

    @Autowired
    private ProductService productService;

    /**
     * Method to save product details
     * @param productDTO object to save product details into collection
     */
    public void saveProductDetails(ProductDTO productDTO) {
        log.info("START :: CLASS :: ProductServiceImpl :: METHOD :: saveProductDetails ::");
        Product product= ProductMapper.toModel(productDTO);
        productService.saveProduct(product);
        log.info("END :: CLASS :: ProductServiceImpl :: METHOD :: saveProductDetails ::");
    }

    /**
     * @param data       that you want pass
     * @param message    as response
     * @param httpStatus status code
     * @param status     1means pass 0 means fail
     * @param <T>        accepts any type of data string number
     * @return api response
     */
    public <T> ApiResponseDTO<T> apiResponse(T data, String message, int httpStatus, int status) {
       log.info("START :: CLASS :: ProductRequestHandler :: METHOD :: apiResponse");
        ApiResponseDTO<T> apiResponseDTO = new ApiResponseDTO<>(
                data,
                message,
                httpStatus,
                status
        );
        log.info("END :: CLASS :: ProductRequestHandler :: METHOD :: apiResponse");
        return apiResponseDTO;
    }
}
