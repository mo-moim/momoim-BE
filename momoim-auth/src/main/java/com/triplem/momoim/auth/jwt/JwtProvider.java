package com.triplem.momoim.auth.jwt;

import com.triplem.momoim.core.domain.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtProperties jwtProperties;

    public TokenInfo generateAccessToken(User user) {
        long accessTokenExpireTime = jwtProperties.getAccessTokenExpireTime();
        return generateToken(user, accessTokenExpireTime, JwtType.ACCESS_TOKEN);
    }

    private TokenInfo generateToken(User user, long expirationMillis, JwtType jwtType) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expirationMillis);

        String accessToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(jwtProperties.getSecretKey(), SignatureAlgorithm.HS512)
                .setId(jwtType.name())
                .compact();

        return TokenInfo.of(accessToken, expiredDate);

    }
}
