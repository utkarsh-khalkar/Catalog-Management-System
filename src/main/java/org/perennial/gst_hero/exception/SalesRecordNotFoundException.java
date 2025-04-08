package org.perennial.gst_hero.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: Utkarsh Khalkar
 * Title:  Custom Exception if sales record not exist
 * Date:   08-04-2025
 * Time:   02:53 PM
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SalesRecordNotFoundException extends RuntimeException {
    public SalesRecordNotFoundException(String message) {
        super(message);
    }
}
