package com.example.todobackend.config.jwt;

import com.example.todobackend.service.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;
    public String generateToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder().signWith(getKey(secret)).setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .claim("roles", getRoles(userPrincipal))
                .claim("zizou","siuuuu")
                .compact();
    }
    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("Expired token");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported token");
        } catch (MalformedJwtException e) {
            logger.error("Malformed token");
        } catch (SignatureException e) {
            logger.error("BadSignature token");
        } catch (IllegalArgumentException e) {
            logger.error("IllegalArgument token");
        } catch (Exception e){
            logger.error("Fail token");
        }
        return false;
    }
    private List<String> getRoles(UserPrincipal principal){
        return principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
    private Key getKey(String secret){
        byte []secretBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
