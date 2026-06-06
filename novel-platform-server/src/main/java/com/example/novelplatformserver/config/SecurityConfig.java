package com.example.novelplatformserver.config;

import com.example.novelplatformserver.config.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 安全配置
 */
@Configuration
@EnableWebSecurity//启用Spring Security的Web安全功能
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF（前后端分离不需要）
                .csrf().disable()
                // 无状态（不创建 Session）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 注册 JWT 过滤器（在 UsernamePasswordAuthenticationFilter 之前执行）
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置接口权限
                .authorizeHttpRequests(auth -> auth
                                // 登录注册放行
                                .requestMatchers(
                                        "/api/v1/auth/register",
                                        "/api/v1/auth/login"
                                ).permitAll()
                                // auth 其他接口（me、logout）需要登录
                                .requestMatchers("/api/v1/auth/**").authenticated()
                                // 公开浏览接口放行
                                .requestMatchers("/api/v1/public/**").permitAll()
                                // Knife4j 文档放行
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/doc.html",
                                        "/webjars/**",
                                        "/static/**",
                                        "/favicon.ico"
                                ).permitAll()
                                // 前台登录用户接口
                                .requestMatchers("/api/v1/portal/**").authenticated()
                                // 作者端接口需要 AUTHOR 或 ADMIN 角色
                                .requestMatchers("/api/v1/author/**").hasAnyRole("AUTHOR", "ADMIN")
                                // 管理端接口需要 ADMIN 角色
                                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                                // 默认拒绝未配置的接口
                                .anyRequest().denyAll()
                )
                // 关掉 Spring Security 自带的登录页面
                .formLogin().disable()
                // 关掉浏览器弹窗输入用户名密码的认证方式
                .httpBasic().disable();

        return http.build();
    }
}
