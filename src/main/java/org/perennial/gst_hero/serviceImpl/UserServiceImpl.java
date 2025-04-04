package org.perennial.gst_hero.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.Entity.Category;
import org.perennial.gst_hero.Entity.User;
import org.perennial.gst_hero.repository.UserRepository;
import org.perennial.gst_hero.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: Utkarsh Khalkar
 * Title:  user services implementation class
 * Date:   28-03-2025
 * Time:   12:25 PM
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @param user object to saved into db
     */
    @Override
    public void saveUser(User user) {
        log.info("START :: CLASS :: UserServiceImpl :: METHOD :: saveUser :: USER_EMAIL ::"+user.getUsername());
        userRepository.save(user);
        log.info("END :: CLASS :: UserServiceImpl :: METHOD :: saveUser :: USER_EMAIL ::"+user.getUsername());
    }

    @Override
    @Transactional
    public Optional<User> findUserById(Long id) {
        log.info("START :: CLASS :: UserServiceImpl :: METHOD :: findUserByIdAndId :: ID ::"+id);
        Optional<User> user=  userRepository.findById(id);
        log.info("END :: CLASS :: UserServiceImpl :: METHOD :: findUserByIdAndId :: ID ::"+id);
        return user;
    }

    /**
     * Method to find user by their username
     * @param username to find
     * @return user
     */
    @Override
    @Transactional
    public Optional<User> findUserByUsername(String username) {
        log.info("START :: CLASS :: UserServiceImpl :: METHOD :: findUserByUsername :: USERNAME ::"+username);
        Optional<User> user = userRepository.findByUsername(username);
        log.info("END :: CLASS :: UserServiceImpl :: METHOD :: findUserByUsername :: USERNAME ::"+username);
        return user;
    }


}
