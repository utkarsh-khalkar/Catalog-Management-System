package org.perennial.gst_hero.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: Utkarsh Khalkar
 * Title:  Custom Exception if user Not Found
 * Date:   08-04-2025
 * Time:   02:51 PM
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
