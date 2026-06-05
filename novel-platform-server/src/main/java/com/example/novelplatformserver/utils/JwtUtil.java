package com.example.novelplatformserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey key;

    @PostConstruct  //Spring 会在依赖注入完成后、Bean 正式投入使用前自动调用这个方法
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 Token
     */
    public String generateToken(Long userId, String username, String role) {
        Date now = new Date();
        return Jwts.builder()
                .id(UUID.randomUUID().toString())  // jti：Token唯一标识，用于黑名单，用userId+签发时间更靠谱（避免同一毫秒冲突）
                .claim("userId", userId)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration * 1000))  // 秒转毫秒
                .signWith(key)
                .compact();
    }

    /**
     * 解析 Token
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 获取用户ID
     */
    public Long getUserId(String token) {
        return parseToken(token).get("userId", Long.class);
    }

    /**
     * 判断 Token 是否过期
     */
    public boolean isExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }

    /**
     * 获取 Token 的唯一标识（用于 Redis 黑名单）
     */
    public String getTokenId(String token) {
        Claims claims = parseToken(token);
        return claims.getId();
    }

    /**
     * 获取 Token 剩余有效时间（秒）
     */
    public long getRemainingTime(String token) {
        Claims claims = parseToken(token);
        long now = System.currentTimeMillis();
        long exp = claims.getExpiration().getTime();
        return Math.max(0, (exp - now) / 1000);
    }
}
