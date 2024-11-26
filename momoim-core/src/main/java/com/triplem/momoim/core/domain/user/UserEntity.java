package com.triplem.momoim.core.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String companyName;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private String socialUid;

    private LocalDateTime createdAt;

    @Builder
    public UserEntity(Long id, String email, String password, String name, String companyName, String profileImage, AccountType accountType,
        String socialUid, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.companyName = companyName;
        this.profileImage = profileImage;
        this.accountType = accountType;
        this.socialUid = socialUid;
        this.createdAt = createdAt;
    }

    public static UserEntity from(User user) {
        return UserEntity.builder()
            .id(user.getId())
            .email(user.getEmail())
            .password(user.getPassword())
            .name(user.getName())
            .companyName(user.getCompanyName())
            .profileImage(user.getProfileImage())
            .accountType(user.getAccountType())
            .socialUid(user.getSocialUid())
            .createdAt(user.getCreatedAt())
            .build();
    }
}
