package org.perennial.gst_hero.mapper;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.CategoryDTO;
import org.perennial.gst_hero.Entity.Category;
import org.perennial.gst_hero.Entity.User;


import java.time.LocalDateTime;

/**
 * Author:  Utkarsh Khalkar
 * Title:   DTO to Entity Mapper
 * Date:    27-03-2025
 * Time:    07:00 PM
 */
@Slf4j
public class CategoryMapper {

    /**
     * @param categoryDTO object to convert into category
     * @param user object to save in category
     * @return category object
     */
    public static Category toModel(CategoryDTO categoryDTO, User user) {
        log.info("START :: CLASS :: CategoryMapper :: METHOD :: toModel :: CategoryCode ::"+categoryDTO.getCategoryCode());
        Category category = new Category();
        category.setCategoryCode(categoryDTO.getCategoryCode());
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryDescription(categoryDTO.getDescription());
        // setting current date and time
        category.setCreatedDate(LocalDateTime.now());
        category.setUser(user);
        log.info("END :: CLASS :: CategoryMapper :: METHOD :: toModel :: CategoryCode ::"+categoryDTO.getCategoryCode());
        return category;
    }
}
