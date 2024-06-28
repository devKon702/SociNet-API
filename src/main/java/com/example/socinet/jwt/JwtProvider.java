package com.example.socinet.jwt;

import com.example.socinet.entity.Account;
import com.example.socinet.security.AccountDetail;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JwtProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final int accessExpired = 60 * 60 * 1000;
    private final int refreshExpired = 30 * 24 * 60 * 60 * 1000;


    public String generateAccessToken(String username){
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + accessExpired);
        log.info(SECRET_KEY);
        return Jwts.builder().setSubject(username)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .compact();
    }

    public String generateRefreshToken(String username){
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + refreshExpired);
        return Jwts.builder().setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String getSubjectFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch(MalformedJwtException e){
            log.error("Token is invalid");
        } catch(ExpiredJwtException e){
            log.error("Token is expired");
        } catch(Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

}
