package com.example.childgarden.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenUtil {

    @Value("s7trSGHGItY70njDEfj7fE8aMqHKK50VUwKcuIClQpRF0cnschildgardeni3iiCI0ADLicD53nXmkeN8c4P5Fg8OdL8aM0HV0qDRv20qI")
    private String jwtSecret;

    private final static Long JWT_TOKEN_VALIDATION = 7 * 24 * 60 * 60 * 1000L;

    private String createToken(Map<String, Object> claims, String subject) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDATION)).signWith(secretKey).compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> objectMap = new HashMap<>();
        return createToken(objectMap, userDetails.getUsername());
    }

    public Date getExpirationFromToken(String token) {
        return getClaimToken(token, Claims::getExpiration);
    }

    private <T> T getClaimToken(String token, Function<Claims, T> function) {
        final Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationFromToken(token);
        return expiration.before(new Date());
    }

    public String getUserNameFromToken(String token) {
        return getClaimToken(token, Claims::getSubject);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}