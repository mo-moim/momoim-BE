package com.triplem.momoim.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // TODO config module application.yml 안에 value 정의

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration defaultConfig = new CorsConfiguration();
        defaultConfig.setAllowedOrigins(List.of("http://localhost:3000", "https://api.momoim.co.kr", "https://www.momoim.co.kr"));
        defaultConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        defaultConfig.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        defaultConfig.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", defaultConfig);
        return source;
    }
}
