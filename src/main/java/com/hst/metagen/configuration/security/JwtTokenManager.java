package com.hst.metagen.configuration.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtTokenManager {

    private static final int validity = 15 * 60 * 1000;
    private String secret="thisismykeythisismykeythisismykeythisismykeythisismykey";


    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

        if (roles.contains(new SimpleGrantedAuthority("ADMIN_USER"))) {
            claims.put("isAdmin", true);
            claims.put("isTeacher", false);
            claims.put("isStudent", false);
        }
        if (roles.contains(new SimpleGrantedAuthority("TEACHER_USER"))) {
            claims.put("isTeacher", true);
            claims.put("isAdmin", false);
            claims.put("isStudent", false);
        }
        if (roles.contains(new SimpleGrantedAuthority("STUDENT_USER"))) {
            claims.put("isStudent", true);
            claims.put("isAdmin", false);
            claims.put("isTeacher", false);
        }

        return doGenerateToken(claims, userDetails.getUsername());

    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS256, secret).compact();

    }

    public boolean tokenValidate(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            throw ex;
        }
    }

    public String getUsernameToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        List<SimpleGrantedAuthority> roles = new ArrayList<>();

        Boolean isAdmin = claims.get("isAdmin", Boolean.class);
        Boolean isTeacher = claims.get("isTeacher", Boolean.class);
        Boolean isStudent = claims.get("isStudent", Boolean.class);

        if (isAdmin != null && isAdmin) {
            roles.add(new SimpleGrantedAuthority("ADMIN_USER"));
        }

        if (isTeacher != null && isTeacher) {
            roles.add(new SimpleGrantedAuthority("TEACHER_USER"));
        }

        if (isStudent != null && isStudent) {
            roles.add(new SimpleGrantedAuthority("STUDENT_USER"));
        }
        return roles;

    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

}