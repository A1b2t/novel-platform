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
                                .requestMatchers("/api/v1/portal/auth/register").permitAll()
                                .requestMatchers("/api/v1/portal/auth/login").permitAll()
                                // Knife4j 文档放行
//                .requestMatchers(
//                    "/swagger-ui/**",
//                    "/v3/api-docs/**",
//                    "/doc.html",
//                    "/webjars/**",//开始出了点小bug，关键修复
                            //Spring Boot已经帮处理了 /static资源映射，所以这里可以不用管
//                    "/**/*.js",
//                    "/**/*.css",
//                    "/**/*.png",
//                    "/**/*.ico"
//                ).permitAll()
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/doc.html",
                                        "/webjars/**",
                                        "/static/**",
                                        "/favicon.ico"  //浏览器标签页上的小图标，不加的话，如果被 Security 拦截了，浏览器控制台会多一个 403 请求，但不影响页面显示
                                ).permitAll()
                                // 其他接口需要认证
                                .anyRequest().authenticated()
                )
                // 关掉 Spring Security 自带的登录页面
                .formLogin().disable()
                // 关掉浏览器弹窗输入用户名密码的认证方式
                .httpBasic().disable();

        return http.build();
    }
}
