package net.microgoose.mocknet.auth.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.config.TokenConfig;
import net.microgoose.mocknet.auth.dto.AuthTokensDto;
import net.microgoose.mocknet.auth.model.UserPrincipal;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final TokenConfig tokenConfig;

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public AuthTokensDto generateTokenPair(String email, Map<String, Object> claims) {
        String accessToken = generateAccessToken(email, claims);
        String refreshToken = generateRefreshToken(email);

        return AuthTokensDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public AuthTokensDto generateTokenPair(UserPrincipal userPrincipal) {
        String email = userPrincipal.getEmail();
        return generateTokenPair(email, null);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(tokenConfig.getJwtSecret().getBytes());
    }

    private String generateAccessToken(String email, Map<String, Object> claims) {
        if (claims == null) {
            claims = new HashMap<>();
        }

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(Date.from(Instant.now().plus(tokenConfig.getAccessTokenExpiration())))
            .setId(UUID.randomUUID().toString())
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private String generateRefreshToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(Date.from(Instant.now().plus(tokenConfig.getRefreshTokenExpiration())))
            .setId(UUID.randomUUID().toString())
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

}