package com.example.novelplatformserver.config.filter;

import com.example.context.UserContext;
import com.example.novelplatformserver.service.TokenService;
import com.example.novelplatformserver.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT 认证过滤器
 * 从请求头解析 Token，设置到 UserContext 和 SecurityContext
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
            String token = null;
            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7);
            } else if (header != null) {
                // 兼容无Bearer 前缀的情况（Knife4j等工具可能直接传Token）
                token = header;
            }

            if (token != null) {
                //检查 Token是否已加入黑名单（退出登录后）
                if (tokenService.isBlacklisted(token)) {
                    filterChain.doFilter(request, response);    //不放行，直接跳过
                    return;
                }

                Claims claims = jwtUtil.parseToken(token);

                //报错了，这种大整数经过Jackson/JJWT解析：极有可能不是 Long，而是 Integer / BigInteger / Number
                //这个坑其实是：JWT Claim类型转换+雪花ID(Long)导致的类型问题
                //Long userId = claims.get("userId", Long.class);   //报错
                Object userIdObj = claims.get("userId");
                Long userId = Long.valueOf(userIdObj.toString());

                //Spring Security 默认角色判断通常是：ROLE_USER
                //String role = claims.get("role", String.class)    //报错
                String role = claims.get("role", String.class).toUpperCase();

                UserContext.setUserId(userId);  //SecurityContextHolder获取id比较麻烦

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            //每个过滤器做完自己的事后，都要调用 filterChain.doFilter() 把请求传给下一个，
            // 比如这里的用户信息的me方法，最终经过过滤器链，最终到 DispatcherServlet → Controller
            filterChain.doFilter(request, response);


        } finally {
            UserContext.clear();

            /*
            Spring Security后续流程（异常处理/AccessDecision/Dispatcher）有时仍然会访问SecurityContext
            SecurityContextHolder.clearContext();*/
        }
    }
}
