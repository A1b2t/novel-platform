package com.example.novelplatformserver.service;

import com.example.novelplatformserver.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Token 管理服务
 * 用 Redis 实现 Token 黑名单，退出登录后 Token 立即失效
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private static final String BLACKLIST_PREFIX = "token:blacklist:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtUtil jwtUtil;

    /**
     * 将 Token 加入黑名单（退出登录时调用）
     */
    public void addToBlacklist(String token) {
        log.info("addToBlacklist token: [{}]", token);
        try {
            String tokenId = jwtUtil.getTokenId(token);
            long expireTime = jwtUtil.getRemainingTime(token);
            if (expireTime > 0) {
                redisTemplate.opsForValue().set(
                        BLACKLIST_PREFIX + tokenId,
                        "1",
                        expireTime,
                        TimeUnit.SECONDS
                );
            }
        } catch (Exception e) {
            log.error("addToBlacklist 失败", e);
        }
    }

    /**
     * 检查 Token 是否在黑名单中
     */
    public boolean isBlacklisted(String token) {
        try {
            String tokenId = jwtUtil.getTokenId(token);
            return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + tokenId));   // //防止redisTemplate.hasKey()返回null
        }catch(JwtException e){
            // token本身有问题
            return true;
        }catch(Exception e){
            log.error("Redis检查黑名单失败", e);
            // Redis异常，不认为在黑名单
            return false;

        }

        /*bug：本意是为了安全，但是：
        isBlacklisted 的 catch 里写了 return true，
        意思是——只要 Redis 连接异常，就认为 Token 在黑名单中。
        isBlacklisted 在 JwtAuthenticationFilter 中调用，
        如果第一次请求时 Redis 还没完全就绪，连接超时，catch 返回 true，
        就会认为 Token 在黑名单中，直接跳过认证了。
        catch (Exception e) {
            return true;    //安全优先
        }*/
    }
}
