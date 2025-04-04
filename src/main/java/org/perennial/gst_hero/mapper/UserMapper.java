package org.perennial.gst_hero.mapper;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.DTO.UserDTO;
import org.perennial.gst_hero.Entity.User;

/**
 * Author: Utkarsh Khalkar
 * Title:  DTO to Entity Mapper
 * Date:   28-03-2025
 * Time:   12:40
 */
@Slf4j
public class UserMapper {

    /**
     * @param dto object
     * @return user entity object
     */
    public static User toEntity(UserDTO dto) {
        log.info("START :: CLASS :: UserMapper :: METHOD :: toEntity :: USER_EMAIL ::"+dto.getUsername());
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        log.info("END :: CLASS :: UserMapper :: METHOD :: toEntity :: USER_EMAIL ::"+dto.getUsername());
        return user;
    }

}
