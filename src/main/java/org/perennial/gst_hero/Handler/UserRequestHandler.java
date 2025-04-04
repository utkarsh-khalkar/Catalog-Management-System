package org.perennial.gst_hero.Handler;
import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.UserDTO;
import org.perennial.gst_hero.Entity.User;
import org.perennial.gst_hero.apiresponsedto.ApiResponseDTO;
import org.perennial.gst_hero.mapper.UserMapper;
import org.perennial.gst_hero.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * Author: Utkarsh Khakar
 * Title:  User Request Handler
 * Date:   28-03-2025
 * Time:   12:38 PM
 */
@Slf4j
@Component
public class UserRequestHandler {

    @Autowired
    private UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * @param userDTO object to save user details
     */
    public void saveUser(UserDTO userDTO) {
        log.info("START :: CLASS :: UserRequestHandler :: METHOD :: saveUser :: UserEmail ::"+userDTO.getUsername());
        User user = UserMapper.toEntity(userDTO);
        // set encrypted password in DB
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        log.info("END :: CLASS :: UserRequestHandler :: METHOD :: saveUser :: UserEmail ::"+userDTO.getUsername());
    }

    /**
     * Method to find user
     * @param username to find
     * @return true of if user present else false
     */
    public Optional<User> findUserByUsername(String username) {
        log.info("START :: CLASS :: UserRequestHandler :: METHOD :: findUserByUsername :: USERNAME ::"+username);
        Optional<User> user=userService.findUserByUsername(username);
        log.info("END :: CLASS :: UserRequestHandler :: METHOD :: findUserByUsername :: USERNAME ::"+username);
        return user;
    }

    /**
     * @param data that you want pass
     * @param message as response
     * @param httpStatus status code
     * @param status  1means pass 0 means fail
     * @return api response
     * @param <T> accepts any type of data string number
     */
    public <T> ApiResponseDTO<T> apiResponse(T data, String message, int httpStatus, int status) {
        log.info("START :: CLASS :: UserRequestHandler :: METHOD :: apiResponse");

        ApiResponseDTO<T> apiResponseDTO = new ApiResponseDTO<>(
                data,
                message,
                httpStatus,
                status
        );

        log.info("END :: CLASS :: UserRequestHandler :: METHOD :: apiResponse");
        return apiResponseDTO;
    }

}
