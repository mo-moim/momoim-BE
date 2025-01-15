package com.triplem.momoim.auth.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Set;

@Component
public class StaticEndpointChecker {
    private final Set<String> endpoints;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public StaticEndpointChecker(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping requestMappingHandlerMapping) {
        int initialCapacity = requestMappingHandlerMapping.getHandlerMethods().size();
        this.endpoints = new HashSet<>(initialCapacity);

        requestMappingHandlerMapping.getHandlerMethods().forEach((key, value) -> {
            if (key.getPathPatternsCondition() != null) {
                key.getPathPatternsCondition().getPatterns().forEach(pattern -> endpoints.add(pattern.getPatternString()));
            }
        });
    }

    public boolean isEndpointExist(String requestURI) {
        return endpoints.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }

    public boolean isEndpointExist(HttpServletRequest request) {
        return isEndpointExist(request.getRequestURI());
    }

    public boolean isEndpointNotExist(HttpServletRequest request) {
        return !isEndpointExist(request.getRequestURI());
    }
}
