package com.triplem.momoim.config;

import com.triplem.momoim.auth.config.JwtAuthenticationEntryPoint;
import com.triplem.momoim.auth.config.filter.JwtFilter;
import com.triplem.momoim.auth.config.handler.CustomAccessDeniedHandler;
import com.triplem.momoim.auth.jwt.JwtProvider;
import com.triplem.momoim.auth.jwt.JwtResolver;
import com.triplem.momoim.auth.utils.StaticEndpointChecker;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] AUTH_WHITELIST = {
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/api-docs/json/**",
        "/api/v1/token/**",
        "/**"
    };

    private final StaticEndpointChecker endpointChecker;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtProvider jwtProvider;
    private final JwtResolver jwtResolver;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .cors(c -> c.configurationSource(corsConfigurationSource))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(req -> req
                .requestMatchers(createFilterList()).permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증 실패 예외 처리
                .accessDeniedHandler(customAccessDeniedHandler) // 권한 없음 예외 처리
            )
            .addFilterBefore(new JwtFilter(jwtProvider, jwtResolver, endpointChecker, List.of(
                "/swagger-ui/**",
                "/api-docs/**",
                "/api/v1/token/**",
                "/**"
            )
            ), UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AntPathRequestMatcher[] createFilterList() {
        return Stream.of(AUTH_WHITELIST)
            .map(AntPathRequestMatcher::new)
            .toArray(AntPathRequestMatcher[]::new);
    }
}
