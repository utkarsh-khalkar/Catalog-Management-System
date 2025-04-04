package org.perennial.gst_hero.repository;

import org.perennial.gst_hero.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Author: Utkarsh Khalkar
 * Title:  Category repository provide database services
 * Date:   27-03-2025
 * Time:   03:49 PM
 */
@Repository
public interface CategoryRepository  extends JpaRepository<Category, String> {


    Optional<Category> findCategoryByCategoryCodeAndCategoryNameAndCategoryDescription(String categoryCode,String categoryName, String categoryDescription);

    @Query(value = "SELECT * FROM category_details WHERE user_id = :userId", nativeQuery = true)
    List<Category> findByUser_Id(@Param("userId") Long id);


}
