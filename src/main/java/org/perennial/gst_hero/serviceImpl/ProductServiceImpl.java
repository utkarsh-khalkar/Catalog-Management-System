package org.perennial.gst_hero.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.Entity.Product;
import org.perennial.gst_hero.repository.ProductRepository;
import org.perennial.gst_hero.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Utkarsh Khalkar
 * Title: Product Service Implemenatation Class
 * Date:  08:04:2025
 * Time:  04:51 PM
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public void saveProduct(Product product) {
        log.info("START :: CLASS :: ProductServiceImpl :: METHOD :: saveProduct :: Product:{}",product);
        productRepository.save(product);
        log.info("END :: CLASS :: ProductServiceImpl :: METHOD :: saveProduct :: Product:{}",product);
    }
}
