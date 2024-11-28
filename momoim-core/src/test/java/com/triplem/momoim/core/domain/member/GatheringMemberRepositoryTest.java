package com.triplem.momoim.core.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
class GatheringMemberRepositoryTest {
    @Autowired
    private GatheringMemberRepository gatheringMemberRepository;

    @Test
    @DisplayName("모임 멤버를 저장할 수 있다.")
    void saveGatheringMember() {
        //given
        GatheringMember gatheringMember = GatheringMember.create(1L, 5L);

        //when
        GatheringMember savedGatheringMember = gatheringMemberRepository.save(gatheringMember);

        //then
        assertThat(savedGatheringMember)
            .extracting("userId", "gatheringId")
            .containsExactly(gatheringMember.getUserId(), gatheringMember.getGatheringId());
    }

    @Test
    @DisplayName("userId와 gatheringId를 통해 등록된 멤버인지 확인하고 등록 된 멤버라면 True를 반환한다.")
    void returnTrueWhenIsRegisteredMember() {
        //given
        Long gatheringId = 1L;
        Long userId = 1L;
        GatheringMember gatheringMember = GatheringMember.create(userId, gatheringId);
        gatheringMemberRepository.save(gatheringMember);

        //when
        Boolean result = gatheringMemberRepository.isExistsByUserIdAndGatheringId(userId, gatheringId);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("userId와 gatheringId를 통해 등록된 멤버인지 확인하고 등록 되지 않은 멤버라면 False를 반환한다.")
    void returnFalseWhenIsNotRegisteredMember() {
        //given
        Long gatheringId = 1L;
        Long userId = 1L;

        //when
        Boolean result = gatheringMemberRepository.isExistsByUserIdAndGatheringId(userId, gatheringId);

        //then
        assertThat(result).isFalse();
    }
}