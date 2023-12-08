package com.Hindol.Blog.Util;
import com.Hindol.Blog.Entity.User;
import com.Hindol.Blog.Payload.TokenValidationResultDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTToken {
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRATION_TIME = 600000;
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expiryDate).signWith(secretKey).compact();
    }
    public TokenValidationResultDTO validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            String email = claims.getSubject();
            return new TokenValidationResultDTO("Valid", email);
        }
        catch (ExpiredJwtException ex) {
            return new TokenValidationResultDTO("Expired Token", null);
        }
        catch (JwtException | IllegalArgumentException e) {
            return new TokenValidationResultDTO("Invalid Token", null);
        }
    }

}
