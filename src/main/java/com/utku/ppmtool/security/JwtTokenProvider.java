package com.utku.ppmtool.security;

import com.utku.ppmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.utku.ppmtool.security.SecurityConstants.EXPIRATION_TIME;
import static com.utku.ppmtool.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        String userId = Long.toString(user.getId());
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("id", Long.toString(user.getId()));
        claimMap.put("username", user.getUsername());
        claimMap.put("fullName", user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claimMap)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid Jwt Signature");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid Jwt Token");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired Jwt Token");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported Jwt Token");
        } catch (IllegalArgumentException e) {
            System.out.println("Jwt claims string is empty");
        }
        return false;
    }

    public Long getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");
        return Long.parseLong(id);
    }

}
