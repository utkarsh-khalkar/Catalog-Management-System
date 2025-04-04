package org.perennial.gst_hero.repository;

import org.perennial.gst_hero.Entity.SalesParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
