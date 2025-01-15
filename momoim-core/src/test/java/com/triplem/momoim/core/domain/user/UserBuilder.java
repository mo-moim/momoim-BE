package com.triplem.momoim.core.domain.user;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class UserBuilder {
    private Long id;
    @Builder.Default
    private String email = "test@test.com";
    @Builder.Default
    private String password = "password";
    @Builder.Default
    private String name = "name";
    @Builder.Default
    private String profileImage = "imageUrl";
    @Builder.Default
    private AccountType accountType = AccountType.EMAIL;
    @Builder.Default
    private String socialUid = "socialUid";
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public User toUser() {
        return User.builder()
            .id(id)
            .email(email)
            .password(password)
            .name(name)
            .profileImage(profileImage)
            .accountType(accountType)
            .socialUid(socialUid)
            .createdAt(createdAt)
            .build();
    }
}
