package com.triplem.momoim.auth;

import com.triplem.momoim.core.domain.user.AccountType;
import com.triplem.momoim.core.domain.user.User;

import java.time.LocalDateTime;

public class AuthFixture {

    public static final User TEST_ADMIN_USER = new User(
            1L,
            "test@test.com",
            "1234",
            "admin",
            "https://s3.profile.com",
            "bio",
            AccountType.EMAIL,
            "socialUid",
            LocalDateTime.now()
    );
}

