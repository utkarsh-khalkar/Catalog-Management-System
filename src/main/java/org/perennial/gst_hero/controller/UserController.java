package org.perennial.gst_hero.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.UserDTO;
import org.perennial.gst_hero.Entity.User;
import org.perennial.gst_hero.Handler.UserRequestHandler;
import org.perennial.gst_hero.apiresponsedto.ApiResponseDTO;
import org.perennial.gst_hero.constant.ErrorMessage;
import org.perennial.gst_hero.constant.SuccessMessage;
import org.perennial.gst_hero.serviceImpl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Optional;

/**
 * Author: Utkarsh Khalkar
 * Title:  User controller to handle user request
 * Date:   28-02-2025
 * Time:   01:04 PM
 */

@Slf4j
@RestController
@RequestMapping("/catalog/user")
public class UserController {

    @Autowired
    private UserRequestHandler userRequestHandler;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<Object>> saveUser( @Valid @RequestBody UserDTO userDTO) {
        log.info("START :: CLASS :: UserController :: METHOD :: saveUser :: UserEmail ::"+userDTO.getUsername());
        Optional<User> isUserExist = userRequestHandler.findUserByUsername(userDTO.getUsername());
        if(isUserExist.isPresent()){
            ApiResponseDTO<Object> apiResponseDTO= userRequestHandler.apiResponse(Collections.emptyList(),
                    ErrorMessage.USER_ALREADY_EXIST,HttpStatus.CONFLICT.value(),ErrorMessage.ERROR_STATUS);
            log.info("ERROR :: CLASS :: UserController :: METHOD :: saveUser :: UserEmail ::"+userDTO.getUsername());
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.CONFLICT);
        }
        userRequestHandler.saveUser(userDTO);

        ApiResponseDTO<Object> apiResponseDTO = new ApiResponseDTO<>(Collections.emptyList(),
                SuccessMessage.USER_CREATED_SUCCESSFULLY,HttpStatus.CREATED.value(), SuccessMessage.SUCCESS_STATUS);

        log.info("END :: CLASS :: UserController :: METHOD :: saveUser :: UserEmail ::"+userDTO.getUsername());
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserDTO userDTO)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (userDTO.getUsername(),userDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(userDTO.getUsername());
        }else {
        return "Login Failed...";
        }
    }

}
