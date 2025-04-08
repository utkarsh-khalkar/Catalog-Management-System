package org.perennial.gst_hero.Handler;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.CategoryDTO;
import org.perennial.gst_hero.Entity.Category;
import org.perennial.gst_hero.Entity.User;
import org.perennial.gst_hero.apiresponsedto.ApiResponseDTO;
import org.perennial.gst_hero.mapper.CategoryMapper;
import org.perennial.gst_hero.service.CategoryService;
import org.perennial.gst_hero.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * Author: Utkarsh Khalkar
 * Title:  Category handler which handle category related request
 * Date:   27:03:2025
 * Time:   03:37 PM
 */
@Slf4j
@Component
public class CategoryRequestHandler {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * @param categoryDTO object to add the category data into db
     */
    public void saveCategory(CategoryDTO categoryDTO) {
        log.info("START :: CLASS :: CategoryRequestHandler METHOD :: saveCategory :: CategoryCode :: "
                + categoryDTO.getCategoryCode());
        Optional<User> user=userService.findUserById(categoryDTO.getUserID());
        Category category = CategoryMapper.toModel(categoryDTO,user.get());
        categoryService.save(category);
        log.info("END :: CLASS :: CategoryRequestHandler METHOD :: saveCategory :: CategoryCode :: "
                + categoryDTO.getCategoryCode());
    }

    /**
     *
     * @param categoryDTO object to check category exist or not
     * @return true if category present
     */
    public boolean findCategoryEntry(CategoryDTO categoryDTO) {
        log.info("START :: CLASS :: CategoryRequestHandler METHOD :: findCategoryEntry :: CategoryCode :: "
                + categoryDTO.getCategoryCode());
        Optional<Category> category = categoryService.findCategoryByCategoryCodeAndCategoryNameAndCategoryDescription(
                categoryDTO.getCategoryCode(),categoryDTO.getCategoryName(),categoryDTO.getDescription());
        log.info("END :: CLASS :: CategoryRequestHandler METHOD :: findCategoryEntry :: CategoryCode :: "
                + categoryDTO.getCategoryCode());
        return category.isPresent() ? true : false;
    }

    /**
     * @param userID to find user
     * @return true if user present else false
     */
    public boolean findUserById(long userID) {
        log.info("START :: CLASS :: CategoryRequestHandler METHOD :: findUserById :: userID ::"+userID);
        Optional<User> user = userService.findUserById(userID);
        log.info("END :: CLASS :: CategoryRequestHandler METHOD :: findUserById :: userID ::"+userID);
        return user.isPresent() ? true : false;
    }

    /**
     * @param id
     * @return
     */
    public List<Category> findAllCategory(long id) {
        log.info("START :: CLASS :: CategoryRequestHandler METHOD :: findAllCategory :: ID ::"+id);
        List<Category> categories=categoryService.findByUserId(id);
        log.info("END :: CLASS :: CategoryRequestHandler METHOD :: findAllCategory :: ID ::"+id);
        return categories;
    }
    public ApiResponseDTO<Object> apiResponse(Object data, String message, HttpStatus httpStatus, int status) {
        log.info("START :: CLASS :: CategoryRequestHandler METHOD :: apiResponse :: ");

        return new ApiResponseDTO<>(
                data != null ? data : Collections.emptyList(),
                message,
                httpStatus.value(),
                status
        );
    }
}
