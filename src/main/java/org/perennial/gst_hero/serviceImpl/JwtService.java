package org.perennial.gst_hero.serviceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Author: Utkarsh Khalkar
 * Title:  Class Provide JWT Related Service
 * Date:   02-03-2025
 * Time:   12:20 PM
 */
@Slf4j
@Service
public class JwtService {


    private final String secretKey;
    public JwtService() {
        this.secretKey = getSecretKey();
    }

    /**
     * This Method generate the secret key
     * @return secret key
     */
    public String getSecretKey() {
        log.info("START :: CLASS :: JwtService :: METHOD :: getSecretKey");
        // it generate secret key
        Key keys= Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // keys.encoded Retrieves the byte array representation of secret key

        String encodedKey= Base64.getEncoder().encodeToString(keys.getEncoded());
        log.info("END :: CLASS :: JwtService :: METHOD :: getSecretKey");
        return encodedKey;
    }
    /**
     * This method generate JWT Token
     * @param username to generate token
     * @return JWT token
     */
    public String generateToken(String username) {
        log.info("START :: CLASS :: JwtService :: METHOD :: generateToken :: USERNAME :: "+username);

        Map<String,Object> claims = new HashMap<>();
        String token= Jwts.builder()
                .setClaims(claims)
                        .setSubject(username)
                                .setIssuedAt(new Date())
                                        .setExpiration(new Date(System.currentTimeMillis() + 1000 *60 * 30))
                                                .signWith(getKey(),SignatureAlgorithm.HS256)
                                                        .compact();

        log.info("END:: CLASS :: JwtService :: METHOD :: generateToken :: USERNAME :: "+username);
        return token;
    }

    /**
     * This method generated Key
     * @return key
     */
    private Key getKey() {
        log.info("START :: CLASS :: JwtService :: METHOD :: getKey");
        byte[] encodedKey = Base64.getDecoder().decode(secretKey);
        Key key=Keys.hmacShaKeyFor(encodedKey);
        log.info("END :: CLASS :: JwtService :: METHOD :: getKey");
        return key;
    }

    /**
     * This method extract username from token
     * @param token to extract username
     * @return extracted username
     */
    public String extractUserName(String token) {
        log.info("START :: CLASS :: JwtService :: METHOD :: extractUserName :: Token ::"+token);
        String username=extractClaim(token,Claims::getSubject);
        log.info("END :: CLASS :: JwtService :: METHOD :: extractUserName :: Token ::"+token);
        return  username;
    }

    /**
     * Validates the provided token against user details.
     * @param token The JWT token
     * @param userDetails The UserDetails object containing user information
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        log.info("START :: CLASS :: JwtService :: METHOD :: validateToken :: Token: {}", userDetails.getUsername());
        boolean isValid = userDetails.getUsername().equals(extractUserName(token)) && !isTokenExpired(token);
        log.info("Token validation result: {}", isValid);
        log.info("END :: CLASS :: JwtService :: METHOD :: validateToken :: Token:{}", userDetails.getUsername());
        return isValid;
    }
    /**
     * Checks if the JWT token has expired.
     * @param token The JWT token
     * @return true if expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        log.info("START :: CLASS :: JwtService :: METHOD :: isTokenExpired :: Token: {}", token);
        boolean expired = extractClaim(token, Claims::getExpiration).before(new Date());
        log.info("END :: CLASS :: JwtService :: METHOD :: isTokenExpired :: Token: {}", token);
        return expired;
    }
    /**
     * Extracts a specific claim from a JWT token.
     * @param token The JWT token
     * @param claimResolver A function to extract a specific claim
     * @return The extracted claim value
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        log.info("START :: CLASS :: JwtService :: METHOD :: extractClaim :: Token ::"+token);
        return claimResolver.apply(extractAllClaims(token));
    }


    /**
     * Extracts all claims from a JWT token.
     * @param token The JWT token
     * @return Claims object containing all claims
     */
    private Claims extractAllClaims(String token) {
        log.info("START :: CLASS :: JwtService :: METHOD :: extractAllClaims :: Token: {}", token);
        Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
        log.info("END :: CLASS :: JwtService :: METHOD :: extractAllClaims :: Token: {}", token);
        return claims;

    }
}
