package com.triplem.momoim.auth.config;

import com.triplem.momoim.auth.utils.StaticEndpointChecker;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String REQUEST_ATTRIBUTE = "exception";
    private static final String RESPONSE_CODE_KEY = "code";
    private static final String RESPONSE_MESSAGE_KEY = "message";

    private final StaticEndpointChecker endpointChecker;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if (endpointChecker.isEndpointExist(request)) {
            ExceptionCode exceptionCode = (ExceptionCode) request.getAttribute(REQUEST_ATTRIBUTE);
            if (exceptionCode != null) {
                setExceptionResponse(exceptionCode, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
        }
    }

    public void generateExceptionHandler(ExceptionCode errorCode, HttpServletResponse response) {
        try {
            setExceptionResponse(errorCode, response);
        } catch (IOException e) {
            throw new BusinessException(ExceptionCode.UNKNOWN_JWT_VALIDATE_ERROR);
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
}
