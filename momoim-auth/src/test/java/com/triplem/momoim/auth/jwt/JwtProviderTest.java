package com.triplem.momoim.auth.jwt;

import com.triplem.momoim.auth.AuthFixture;
import com.triplem.momoim.auth.AuthUser;
import com.triplem.momoim.core.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class JwtProviderTest {

    private User user;

    @BeforeEach
    public void setUp(){
        user = AuthFixture.TEST_ADMIN_USER;
    }

    @Nested
    @DisplayName("Access Token 생성 테스트")
    class AccessTokenTest {

        @BeforeEach
        @DisplayName("Access token 생성이 성공한다.")
        @Test
        void generateAccessToken() {
            // given
            JwtProvider jwtProvider = JwtFixture.JWT_PROVIDER();
            JwtResolver jwtResolver = JwtFixture.JWT_RESOLVER();
            TokenInfo tokenInfo = jwtProvider.generateAccessToken(user);

            // when
            AuthUser authUser = jwtResolver.resolveAccessToken(tokenInfo.getValue());

            // then
            Assertions.assertThat(authUser.id()).isEqualTo(user.getId());
        }

        // TODO: exception message를 custom error code로 교체
        @DisplayName("Access token이 잘못된 secret key로 생성한 뒤 resolve를 하면 에러가 발생한다.")
        @Test
        void resolveAccessTokenWithInvalidSecretKey() {
            // given
            JwtProvider jwtProvider = JwtFixture.JWT_PROVIDER_WITH_INVALID_SECRET_KEY();
            JwtResolver jwtResolver = JwtFixture.JWT_RESOLVER();
            TokenInfo tokenInfo = jwtProvider.generateAccessToken(user);

            // when & then
            Assertions.assertThatCode(() -> jwtResolver.resolveAccessToken(tokenInfo.getValue()))
                    .isInstanceOf(Exception.class)
                    .hasMessage("Invalid jwt exception");
        }

        @DisplayName("만료된 Access token으로 resolve를 하면 에러가 발생한다.")
        @Test
        void resolveAccessTokenWithExpiredToken() {
            // given
            JwtProvider jwtProvider = JwtFixture.JWT_PROVIDER_WITH_EXPIRED_TOKEN();
            JwtResolver jwtResolver = JwtFixture.JWT_RESOLVER_WITH_EXPIRED_TOKEN();
            TokenInfo tokenInfo = jwtProvider.generateAccessToken(user);

            // when & then
            Assertions.assertThatCode(() -> jwtResolver.resolveAccessToken(tokenInfo.getValue()))
                    .isInstanceOf(Exception.class)
                    .hasMessage("Expired jwt exception");
        }

        @DisplayName("토큰 형태가 잘못된 access token으로 resolve를 하면 에러가 발생한다.")
        @Test
        void resolveAccessTokenWithInvalidToken() {
            // given
            String invalidToken = "malformed";
            JwtResolver jwtResolver = JwtFixture.JWT_RESOLVER();

            // when & then
            Assertions.assertThatCode(() -> jwtResolver.resolveAccessToken(invalidToken))
                    .isInstanceOf(Exception.class)
                    .hasMessage("Invalid jwt exception");
        }
    }
}