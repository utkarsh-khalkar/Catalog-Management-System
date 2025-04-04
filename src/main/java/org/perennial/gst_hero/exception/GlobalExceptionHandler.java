package org.perennial.gst_hero.exception;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.apiresponsedto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @param ex argument not validation.
     * @return map with field name and message of invalid field.
     */
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ApiResponseDTO<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        log.info("START :: CLASS :: GlobalExceptionHandler :: METHOD :: handleMethodArgumentNotValidException :: Argument ::"+ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        Map<String, String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field=((FieldError)error).getField();
            String message=error.getDefaultMessage();
            response.put(field, message);

        });
        // return response
        ApiResponseDTO<Object> apiResponseDTO = new ApiResponseDTO<>(
                response,
                "Validation Error",
                HttpStatus.BAD_REQUEST.value(),
                0
        );
        log.info("END :: CLASS :: GlobalExceptionHandler :: METHOD :: handleMethodArgumentNotValidException :: Argument ::"+ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
