package org.perennial.gst_hero.service;

import org.perennial.gst_hero.Entity.User;

import java.util.Optional;

/**
 * Author: Utkarsh Khalkar
 * Title:  user service provider
 * Date:   28-03-2025
 * Time:   12:22 PM
 */
public interface UserService {

    void saveUser(User user);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);



}
