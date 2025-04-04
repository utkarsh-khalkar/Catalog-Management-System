package org.perennial.gst_hero.repository;
import org.perennial.gst_hero.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Author: Utkarsh Khalkar
 * Title:  user repo to add the user data to db
 * Date:   28-03-2025
 * Time:   12:20 PM
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


   // User findByUsername(String username);
    Optional<User> findByUsername(String username);

}
