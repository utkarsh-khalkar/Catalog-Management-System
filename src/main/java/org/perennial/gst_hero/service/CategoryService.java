package org.perennial.gst_hero.service;

import org.perennial.gst_hero.Entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: Utkarsh Khalkar
 * Title: Category Service Provider
 * Date:  27:03:2025
 * Time:  03:45 PM
 */

public interface CategoryService {

    void  save(Category category);
    List<Category> getAll();
    void delete(String categoryCode);
    Optional<Category> findCategoryByCategoryCodeAndCategoryNameAndCategoryDescription(String categoryCode,String categoryName, String categoryDescription);
    List<Category> findByUserId(Long id);
}
