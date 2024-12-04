package com.triplem.momoim.auth.config.filter;

import com.triplem.momoim.auth.AuthUser;
import com.triplem.momoim.auth.config.UserAuthentication;
import com.triplem.momoim.auth.jwt.JwtResolver;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String PREFIX_BEARER = "Bearer ";
    private static final String PREFIX_AUTHORIZATION = "Authorization";
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String RESPONSE_CODE_KEY = "code";
    private static final String RESPONSE_MESSAGE_KEY = "message";

    private final JwtResolver jwtResolver;

    @Override
    protected void doFilterInternal(
        @Nonnull HttpServletRequest request,
        @Nonnull HttpServletResponse response,
        @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        boolean occurJWTException = false;
        try {
            String jwt = getJwtFromRequest(request);
            AuthUser authUser = jwtResolver.resolveAccessToken(jwt);
            Long userId = authUser.id();
            UserAuthentication authentication = new UserAuthentication(userId, "USER");
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BusinessException e) {
            if (e.getExceptionCode().equals(ExceptionCode.EXPIRED_JWT) || e.getExceptionCode().equals(ExceptionCode.UNKNOWN_JWT_VALIDATE_ERROR)) {
                setExceptionResponse(e.getExceptionCode(), response);
                occurJWTException = true;
            }
        } finally {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                setGuestAuthentication(request);
            }
            if (!occurJWTException) {
                filterChain.doFilter(request, response);
            }
        }
    }

    private void setExceptionResponse(ExceptionCode exceptionCode, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseBody = new JSONObject();
        responseBody.put(RESPONSE_CODE_KEY, exceptionCode.getCode());
        responseBody.put(RESPONSE_MESSAGE_KEY, exceptionCode.getMessage());

        response.getWriter().print(responseBody);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(PREFIX_AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX_BEARER)) {
            return bearerToken.substring(PREFIX_BEARER.length());
        }
        throw new BusinessException(ExceptionCode.UNSUPPORTED_BEARER_FORMAT);
    }

    private void setGuestAuthentication(HttpServletRequest request) {
        UserAuthentication authentication = new UserAuthentication(-1L, "GUEST");
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
