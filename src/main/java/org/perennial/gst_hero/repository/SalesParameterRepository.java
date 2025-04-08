package org.perennial.gst_hero.repository;

import org.perennial.gst_hero.Entity.SalesParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Author: Utkarsh Khalkar
 * Title:  SalesParameter Repository to save parameter data
 * Date:   03-04-2025
 * Time:   12:45 PM
 */
@Repository
public interface SalesParameterRepository extends JpaRepository<SalesParameter, Long> {

    List<SalesParameter> findByStatus(String status);
    SalesParameter findByFinancialYear(String financialYear);
    @Query(value = "SELECT * FROM sales_parameter s " +
            "WHERE (:financialYear IS NULL OR s.financial_year = :financialYear) " +
            "AND (:startDate IS NULL OR s.start_date >= :startDate) " +
            "AND (:endDate IS NULL OR s.start_date <= :endDate) " +
            "AND (:month IS NULL OR MONTH(s.created_at) = :month) " +
            "AND (:userId IS NULL OR s.user_id = :userId) ",
            nativeQuery = true)
    SalesParameter findByAllParam(
            @Param("financialYear") String financialYear,
            @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Param("month") String month,
            @Param("userId") Long userId
    );
}
