package com.triplem.momoim.auth.jwt;

import com.triplem.momoim.auth.AuthUser;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtResolver {
    private final JwtProperties jwtProperties;

    public AuthUser resolveAccessToken(String token) {
        return resolveToken(token);
    }

    /**
     * Internal method to parse and validate a JWT token. This method validates the token signature, structure, and expiration, and extracts claims to
     * construct an AuthUser object.
     *
     * @param token the JWT token to resolve
     * @return AuthUser instance containing user details from the token
     * @throws RuntimeException for various JWT parsing and validation errors
     * @apiNote Replace RuntimeException with CustomException and a well-defined ErrorCode enum for better error categorization and handling.
     */
    private AuthUser resolveToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

            Long userId = Long.valueOf(claims.getSubject());
            return AuthUser.from(userId);

        } catch (ExpiredJwtException e) {
            throw new BusinessException(ExceptionCode.EXPIRED_JWT);
        } catch (JwtException e) {
            throw new BusinessException(ExceptionCode.UNKNOWN_JWT_VALIDATE_ERROR);
        }
    }
}
