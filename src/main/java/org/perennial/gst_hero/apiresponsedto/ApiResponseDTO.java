package org.perennial.gst_hero.apiresponsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Utkarsh Khalkar
 * Title: API Response DTO to return Reponse
 * Date: 28-03-2025
 * Time: 09:49 AM
 * @param <T>
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponseDTO<T>{
    private T data;
    private String message;
    private int statusCode;
    private int status;
}
