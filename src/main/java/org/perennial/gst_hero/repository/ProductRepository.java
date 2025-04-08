package org.perennial.gst_hero.repository;

import org.perennial.gst_hero.Entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Utkarsh Khalkar
 * Title:  Product Repository to save product details
 * Date:   08:04:2025
 * Time:   4:45 PM
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    void deleteByProductName(String productName);
}
