package com.triplem.momoim.core.domain.user;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String profileImage;
    private AccountType accountType;
    private String socialUid;
    private LocalDateTime createdAt;
}
