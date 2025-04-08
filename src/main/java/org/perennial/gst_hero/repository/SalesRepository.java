package org.perennial.gst_hero.repository;

import org.perennial.gst_hero.Entity.Purchase;
import org.perennial.gst_hero.Entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Author: Utkarsh Khalkar
 * Title: Sales repository to save sales related data
 * Date:  03-04-2025
 * Time:  09:45 AM
 */
@Repository
public interface SalesRepository extends JpaRepository<Sales,Long> {
    List<Sales> findByFinancialYear(String financialYear);
    @Query(value = "SELECT * FROM sales_details WHERE financial_year = :financialYear LIMIT :batchSize OFFSET :offset",
            nativeQuery = true)
    List<Sales> findSalesByFinancialYearWithBatchSize(@Param("financialYear") String financialYear,
                                                      @Param("batchSize") int batchSize,
                                                      @Param("offset") int offset);

    int countByFinancialYear(String financialYear);

    @Query(value = "SELECT * FROM sales_details s " +
            "WHERE (:financialYear IS NULL OR s.financial_year = :financialYear) " +
            "AND (:startDate IS NULL OR s.created_at >= :startDate) " +
            "AND (:endDate IS NULL OR s.created_at <= :endDate) " +
            "AND (:month IS NULL OR MONTH(s.created_at) = :month) " +
            "AND (:userId IS NULL OR s.user_id = :userId) " +
            "LIMIT :batchSize OFFSET :offset",
            nativeQuery = true)
    List<Sales> findSalesDetailsWithBatchSize(
            @Param("financialYear") String financialYear,
            @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Param("month") String month,
            @Param("userId") Long userId,
            @Param("batchSize") int batchSize,
            @Param("offset") int offset
    );

    @Query(value = "SELECT count(*) FROM sales_details s " +
            "WHERE (:financialYear IS NULL OR s.financial_year = :financialYear) " +
            "AND (:startDate IS NULL OR s.created_at >= :startDate) " +
            "AND (:endDate IS NULL OR s.created_at <= :endDate) " +
            "AND (:month IS NULL OR MONTH(s.created_at) = :month) " +
            "AND (:userId IS NULL OR s.user_id = :userId) ",
            nativeQuery = true)
    int getCountOfSalesRecord(
            @Param("financialYear") String financialYear,
            @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Param("month") String month,
            @Param("userId") Long userId
    );

    void deleteByCreatedAt(LocalDate date);

    @Query(value = "SELECT * FROM sales_details WHERE sales_id = :salesId", nativeQuery = true)
    Sales findBySales_id(@Param("salesId") long salesId);




}
