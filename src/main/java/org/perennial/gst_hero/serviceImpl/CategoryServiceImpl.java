package org.perennial.gst_hero.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.Entity.Category;
import org.perennial.gst_hero.repository.CategoryRepository;
import org.perennial.gst_hero.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Author: Utkarsh Khalkar
 * Title: Service Implementation provide category services
 * Date:  27-03-2025
 * Time:  03:55 PM
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    /**
     * method to add category in DB
     * @param category object that need store in DB
     */
    @Override
    public void save(Category category) {
        log.info("START :: CLASS :: CategoryServiceImpl :: METHOD :: save :: Category Code ::"+category.getCategoryCode());
        categoryRepository.save(category);
        log.info("END:: CLASS :: CategoryServiceImpl :: METHOD :: save :: Category Code ::"+category.getCategoryCode());

    }
    /**
     * @return list of all category
     */
    @Override
    public List<Category> getAll() {
        log.info("START :: CLASS :: CategoryServiceImpl :: METHOD :: getAll ");
        List<Category> categoryList = categoryRepository.findAll();
        log.info("END :: CLASS :: CategoryServiceImpl :: METHOD :: getAll Category ");
        return categoryList;
    }




    @Override
    public void delete(String categoryCode) {

    }
    /**
     *
     * @param categoryCode for finding category from db
     * @param categoryName for finding category from db
     * @param categoryDescription for finding category from db
     * @return category
     */
    @Override
    public Optional<Category> findCategoryByCategoryCodeAndCategoryNameAndCategoryDescription(String categoryCode, String categoryName, String categoryDescription) {
        log.info("START :: CLASS :: CategoryServiceImpl :: METHOD :: findCategoryByCategoryCodeAndCategoryNameAndCategoryDescription :: Category Code ::"+categoryCode);
        Optional<Category> category= categoryRepository.findCategoryByCategoryCodeAndCategoryNameAndCategoryDescription(categoryCode,categoryName,categoryDescription);
        log.info("END :: CLASS :: CategoryServiceImpl :: METHOD :: findCategoryByCategoryCodeAndCategoryNameAndCategoryDescription :: Category Code ::"+categoryCode);
        return category;
    }

    /**
     * @param id to find categories
     * @return list of categories
     */
    @Override
    public List<Category> findByUserId(Long id) {
        log.info("START :: CLASS :: CategoryServiceImpl :: METHOD :: findByUserId :: ID ::"+id);
        List<Category> categories=categoryRepository.findByUser_Id(id);
        log.info("END :: CLASS :: CategoryServiceImpl :: METHOD :: findByUserId :: ID ::"+id);
        return categories;
    }
}
