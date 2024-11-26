package com.triplem.momoim.core.domain.user;

import lombok.Getter;

@Getter
public enum AccountType {
    EMAIL(), KAKAO();

    AccountType() {
    }
}
