package org.perennial.gst_hero.repository;

import org.perennial.gst_hero.Entity.PurchaseParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Author: Utkarsh Khalkar
 * Title:  Purchase parameter repository to save parameters details
 * Date:   07:04:2025
 * Time:   11:30 AM
 */
public interface PurchaseParameterRepository extends JpaRepository<PurchaseParameter, Long> {

    List<PurchaseParameter> findByStatus(String status);


    @Query(value = "SELECT * FROM purchase_parameter p " +
            "WHERE p.seller_name = :sellerName " +
            "AND p.financial_year = :financialYear " +
            "AND MONTH(p.created_at) = :month " +
            "AND p.user_id = :userId", nativeQuery = true)
    List<PurchaseParameter> findByAllParams(
            @Param("sellerName") String sellerName,
            @Param("financialYear") String financialYear,
            @Param("month") String month,
            @Param("userId") String userId);
}
