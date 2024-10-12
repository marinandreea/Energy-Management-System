package com.example.DS_Assignment1_B2_Devices.security;

import com.example.DS_Assignment1_B2_Devices.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Service
public class JwtService {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final String SECRET_KEY = "2948404D635166546A576E5A7134743777217A25432A462D4A614E645267556B";
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000000 * 60 * 240))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token){
        final String username = extractUsername(token);
        return (isTokenSignatureValid(token) && !isTokenExpired(token));
    }

    private boolean isTokenSignatureValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Token signature validation failed
            return false;
        }
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public void setAuthenticationInContext(String token, HttpServletRequest request) {
        final String username = extractUsername(token);
        System.out.println(username);

        Claims claims = Jwts.parser()
                .setSigningKey(getSignInKey()) // Set your JWT secret key here
                .parseClaimsJws(token)
                .getBody();

       String rolesClaim = claims.get("role", String.class);

        CustomUser userDetails = (CustomUser) customUserDetailsService.loadUserByUsernameAndToken(username, token);
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(rolesClaim);

        if (userDetails != null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, Collections.singleton(simpleGrantedAuthority));
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

}
