package org.perennial.gst_hero.repository;

import org.perennial.gst_hero.Entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Author: Utkarsh Khakar
 * Title:  Purchase Repo to save purchase data
 * Date:   07:04:2025
 * Time:   09:59 AM
 */
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query(value = "SELECT count(*) FROM purchase_details p " +
            "WHERE (:financialYear IS NULL OR p.financial_year = :financialYear) " +
            "AND (:month IS NULL OR MONTH(p.created_at) = :month) " +
            "AND (:sellerName IS NULL OR p.seller_name = :sellerName) " +
            "AND (:userId IS NULL OR p.user_id = :userId)",
            nativeQuery = true)
    int getCountOfPurchaseDetails(
            @Param("financialYear") String financialYear,
            @Param("month") String month,
            @Param("sellerName") String sellerName,
            @Param("userId") long userId
    );



    @Query(value = "SELECT * FROM purchase_details p " +
            "WHERE (:financialYear IS NULL OR p.financial_year = :financialYear) " +
            "AND (:month IS NULL OR MONTH(p.created_at) = :month) " +
            "AND (:sellerName IS NULL OR p.seller_name = :sellerName) " +
            "AND (:userId IS NULL OR p.user_id = :userId) " +
            "LIMIT :batchSize OFFSET :offset",
            nativeQuery = true)
    List<Purchase> findPurchaseDetailsWithBatchSize(
            @Param("financialYear") String financialYear,
            @Param("month") String month,
            @Param("sellerName") String sellerName,
            @Param("userId") long userId,
            @Param("batchSize") int batchSize,
            @Param("offset") int offset
    );

//    @Query(value = "SELECT * FROM purchase_details p " +
//            "WHERE (:financialYear IS NULL OR p.financial_year = :financialYear) " +
//            "AND (:month IS NULL OR MONTH(p.created_at) = :month) " +
//            "AND (:sellerName IS NULL OR p.seller_name = :sellerName) " +
//            "AND (:userId IS NULL OR p.user_id = :userId)",
//            nativeQuery = true)
//    Optional<List<Purchase>> isPurchaseDetailsPresent(
//            @Param("financialYear") String financialYear,
//            @Param("month") String month,
//            @Param("sellerName") String sellerName,
//            @Param("userId") long userId
//    );

    void deleteByCreatedAt(LocalDate date);

    @Query(value = "SELECT * FROM purchase_details WHERE purchase_id = :purchaseId",nativeQuery = true)
    Purchase findByPurchaseId(@Param("purchaseId") long purchaseId);

}
