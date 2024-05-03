package com.project.EzyWallet.UserService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.validation.length}")
    private long jwtValidationLength;

    @Value("${jwt.validation.signkey}")
    private String signkey;

    public String extractUsername(String token) {
        return extractClaimDetails(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaimDetails(token, Claims::getExpiration);
    }

    public <R> R extractClaimDetails(String token, Function<Claims, R> function){
        Claims claims = extractAllClaims(token);
        return function.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isExpired(String token) {
//        try {
            return extractExpiration(token).before(new Date(System.currentTimeMillis()));
//        }catch(ExpiredJwtException e){
//            return true;
//        }
    }

    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+jwtValidationLength*1000))
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] stream = Decoders.BASE64.decode(this.signkey);
        return Keys.hmacShaKeyFor(stream);
    }
}
