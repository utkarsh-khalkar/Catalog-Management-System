package org.perennial.gst_hero.mapper;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.ProductDTO;
import org.perennial.gst_hero.Entity.Product;

import java.time.LocalDate;

/**
 * Author: Utkarsh Khalkar
 * Title:  Product DTO to Entity Mapper Class
 * Date:   08:04:2025
 * Time:   04:35 PM
 */
@Slf4j
public class ProductMapper {

    public static Product toModel(ProductDTO productDTO) {
        log.info("START :: CLASS :: ProductMapper :: METHOD :: toModel");
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCreatedAt(LocalDate.now());
        product.setAttributes(productDTO.getAttributes());
        product.setQuantity(productDTO.getQuantity());
        product.setCategoryCode(productDTO.getCategoryCode());
        product.setUserId(productDTO.getUserId());
        log.info("END :: CLASS :: ProductMapper :: METHOD :: toModel");
        return product;
    }
}
