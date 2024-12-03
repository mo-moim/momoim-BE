package com.triplem.momoim.core.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringBuilder;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GatheringMemberRemoverTest {
    @Autowired
    private GatheringMemberRemover gatheringMemberRemover;

    @Autowired
    private GatheringRepository gatheringRepository;

    @Autowired
    private GatheringMemberRepository gatheringMemberRepository;

    @Test
    @DisplayName("멤버를 모임에서 뺄 수 있다.")
    void removeGatheringMember() {
        //given
        Long gatheringMemberUserId = 1L;
        Gathering savedGathering = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        gatheringMemberRepository.save(GatheringMember.create(gatheringMemberUserId, savedGathering.getId()));

        //when
        gatheringMemberRemover.removeGatheringMember(gatheringMemberUserId, savedGathering.getId());

        //then
        Boolean isGatheringMember = gatheringMemberRepository.isGatheringMember(gatheringMemberUserId, savedGathering.getId());
        assertThat(isGatheringMember).isFalse();

        Gathering gathering = gatheringRepository.findById(savedGathering.getId());
        assertThat(gathering.getParticipantCount()).isEqualTo(savedGathering.getParticipantCount() - 1);
    }

    @Test
    @DisplayName("참여중인 모임 멤버가 아니라면 예외가 발생한다.")
    void throwExceptionWhenIsNotGatheringMember() {
        //given
        Long notGatheringMemberUserId = 1L;
        Gathering gathering = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());

        //when then
        assertThatThrownBy(() -> gatheringMemberRemover.removeGatheringMember(notGatheringMemberUserId, gathering.getId()))
            .hasMessage("참여중인 모임이 아닙니다.");
    }
}