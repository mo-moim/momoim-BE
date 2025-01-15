package com.triplem.momoim.auth.utils;

import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {
    public static Long getMemberIdByPrincipal() {
        Object principal = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .orElseThrow(() -> new BusinessException(ExceptionCode.INVALID_MEMBER_PROFILE_REQUEST));
        try {
            return (long) principal;
        } catch (ClassCastException e) {
            throw new BusinessException(ExceptionCode.INVALID_PRINCIPAL);
        }
    }
}
