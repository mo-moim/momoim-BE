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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String companyName;

    private String profileImage;

    private String biography;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private String socialUid;

    private LocalDateTime createdAt;

    public static UserEntity from(User user) {
        return UserEntity.builder()
            .id(user.getId())
            .email(user.getEmail())
            .password(user.getPassword())
            .name(user.getName())
            .companyName(user.getCompanyName())
            .profileImage(user.getProfileImage())
            .biography(user.getBiography())
            .accountType(user.getAccountType())
            .socialUid(user.getSocialUid())
            .createdAt(user.getCreatedAt())
            .build();
    }

    public User toModel() {
        return User.builder()
            .id(id)
            .email(email)
            .password(password)
            .name(name)
            .companyName(companyName)
            .profileImage(profileImage)
            .biography(biography)
            .accountType(accountType)
            .socialUid(socialUid)
            .createdAt(createdAt)
            .build();
    }
}
