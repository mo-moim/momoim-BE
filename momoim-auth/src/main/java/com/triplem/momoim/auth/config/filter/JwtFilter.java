package com.triplem.momoim.auth.config.filter;

import com.triplem.momoim.auth.AuthUser;
import com.triplem.momoim.auth.config.UserAuthentication;
import com.triplem.momoim.auth.jwt.JwtProvider;
import com.triplem.momoim.auth.jwt.JwtResolver;
import com.triplem.momoim.auth.utils.StaticEndpointChecker;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String PREFIX_BEARER = "Bearer ";
    private static final String PREFIX_AUTHORIZATION = "Authorization";
    private static final String PREFIX_UN_AUTHORIZATION = "UnAuthorization";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String RESPONSE_CODE_KEY = "code";
    private static final String RESPONSE_MESSAGE_KEY = "message";

//    private static final List<String> whiteListPatterns = List.of(
//            "/swagger-ui/**",
//            "/api-docs/**",
//            "/api/v1/token/**"
//    );

    private final JwtProvider jwtProvider;
    private final JwtResolver jwtResolver;

    private final StaticEndpointChecker endpointChecker;
    private final List<String> whiteListPatterns;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
//        if (isWhiteListRequest(request) || endpointChecker.isEndpointNotExist(request)) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        try {
            String jwt = getJwtFromRequest(request);
            AuthUser authUser = jwtResolver.resolveAccessToken(jwt);
            Long userId = authUser.id();
            UserAuthentication authentication = new UserAuthentication(userId, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        } catch (BusinessException e) {
            setExceptionResponse(e.getExceptionCode(), response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 인증 정보입니다.");
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(PREFIX_AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX_BEARER)) {
            return bearerToken.substring(PREFIX_BEARER.length());
        }
        throw new BusinessException(ExceptionCode.UNSUPPORTED_BEARER_FORMAT);
    }

    private void setExceptionResponse(ExceptionCode exceptionCode, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseBody = new JSONObject();
        responseBody.put(RESPONSE_CODE_KEY, exceptionCode.getCode());
        responseBody.put(RESPONSE_MESSAGE_KEY, exceptionCode.getMessage());

        response.getWriter().print(responseBody);
    }

    private boolean isWhiteListRequest(HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        return whiteListPatterns.stream()
                .anyMatch(pattern -> antPathMatcher.match(pattern, requestUri));
    }
}
