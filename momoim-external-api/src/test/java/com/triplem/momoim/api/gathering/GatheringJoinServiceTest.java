package com.triplem.momoim.api.gathering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import com.triplem.momoim.core.domain.member.GatheringMember;
import com.triplem.momoim.core.domain.member.GatheringMemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
class GatheringJoinServiceTest {
    @Autowired
    private GatheringJoinService gatheringJoinService;

    @Autowired
    private GatheringRepository gatheringRepository;

    @Autowired
    private GatheringMemberRepository gatheringMemberRepository;

    @Test
    @DisplayName("모임에 참여할 수 있다.")
    void joinGathering() {
        //given
        Gathering gathering = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        Long newMemberUserId = 1L;

        //when
        gatheringJoinService.joinGathering(newMemberUserId, gathering.getId());

        //then
        Boolean isSuccessJoin = gatheringMemberRepository.isGatheringMember(newMemberUserId, gathering.getId());
        assertThat(isSuccessJoin).isTrue();
    }

    @Test
    @DisplayName("모임 참여 취소를 할 수 있다.")
    void cancelJoinGathering() {
        //given
        Long memberUserId = 1L;
        Gathering gathering = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        GatheringMember gatheringMember = gatheringMemberRepository.save(GatheringMember.create(memberUserId, gathering.getId()));

        //when
        gatheringJoinService.cancelJoinGathering(memberUserId, gathering.getId());

        //then
        Boolean isGatheringMember = gatheringMemberRepository.isGatheringMember(memberUserId, gathering.getId());
        assertThat(isGatheringMember).isFalse();
    }

    @Test
    @DisplayName("이미 종료된 모임은 참여 취소를 할 수 없다.")
    void cannotCancelJoinGatheringForEndGathering() {
        //given
        Long memberUserId = 1L;
        Gathering gathering = gatheringRepository.save(GatheringBuilder
            .builder()
            .endAt(LocalDateTime.now().minusDays(10))
            .build()
            .toGathering()
        );
        gatheringMemberRepository.save(GatheringMember.create(memberUserId, gathering.getId()));

        //when then
        assertThatThrownBy(() -> gatheringJoinService.cancelJoinGathering(memberUserId, gathering.getId()))
            .hasMessage("종료 된 모임입니다.");

        Boolean isGatheringMember = gatheringMemberRepository.isGatheringMember(memberUserId, gathering.getId());
        assertThat(isGatheringMember).isTrue();
    }
}