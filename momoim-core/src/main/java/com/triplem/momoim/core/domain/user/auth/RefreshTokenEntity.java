package com.triplem.momoim.core.domain.user.auth;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Entity
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String token;

    private Date expiredAt;

    public static RefreshTokenEntity from(RefreshToken refreshToken) {
        return RefreshTokenEntity.builder()
                .id(refreshToken.getId())
                .userId(refreshToken.getUserId())
                .token(refreshToken.getToken())
                .expiredAt(refreshToken.getExpiredAt())
                .build();
    }

    public RefreshToken toModel() {
        return RefreshToken.builder()
                .id(id)
                .userId(userId)
                .token(token)
                .expiredAt(expiredAt)
                .build();
    }
}
