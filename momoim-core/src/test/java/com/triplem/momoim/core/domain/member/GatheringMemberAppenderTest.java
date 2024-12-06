package com.triplem.momoim.core.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringBuilder;
import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GatheringMemberAppenderTest {
    @Autowired
    private GatheringMemberAppender gatheringMemberAppender;

    @Autowired
    private GatheringRepository gatheringRepository;

    @Autowired
    private GatheringMemberRepository gatheringMemberRepository;

    @Test
    @DisplayName("모임에 멤버를 추가 할 수 있다.")
    void appendGatheringMember() {
        //given
        Gathering gathering = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        Long newMemberUserId = 2L;

        //when
        gatheringMemberAppender.append(newMemberUserId, gathering.getId());

        // then
        Boolean isSuccessAppend = gatheringMemberRepository.isGatheringMember(newMemberUserId, gathering.getId());
        assertThat(isSuccessAppend).isTrue();

        Gathering savedGathering = gatheringRepository.findById(gathering.getId());
        assertThat(savedGathering.getParticipantCount()).isEqualTo(gathering.getParticipantCount() + 1);
    }

    @Test
    @DisplayName("인원이 다 찬 모임은 멤버를 추가 할 수 없다.")
    void cannotAppendWhenIsFull() {
        //given
        Gathering fullGathering = gatheringRepository.save(
            GatheringBuilder
                .builder()
                .capacity(10)
                .participantCount(10)
                .build()
                .toGathering()
        );
        Long newMemberUserId = 2L;

        //when then
        assertThatThrownBy(() -> gatheringMemberAppender.append(newMemberUserId, fullGathering.getId()))
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.FULL_PARTICIPANT_GATHERING);

        Boolean isSuccessAppend = gatheringMemberRepository.isGatheringMember(newMemberUserId, fullGathering.getId());
        assertThat(isSuccessAppend).isFalse();
    }

    @Test
    @DisplayName("이미 모임에 참가중인 유저는 멤버로 추가 될 수 없다.")
    void cannotAppendWhenIsAlreadyMember() {
        //given
        Gathering gathering = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        GatheringMember member1 = GatheringMember.create(2L, gathering.getId());
        gatheringMemberRepository.save(member1);

        //when then
        assertThatThrownBy(() -> gatheringMemberAppender.append(member1.getUserId(), gathering.getId()))
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.ALREADY_JOINED_GATHERING);
    }

    @Test
    @DisplayName("존재하지 않는 모임에 멤버가 추가 될 수 없다.")
    void cannotAppendWhenIsNotExistsGathering() {
        //given
        Long notExistsGatheringId = 123L;
        Long newMemberUserId = 2L;
        //when then
        assertThatThrownBy(() -> gatheringMemberAppender.append(notExistsGatheringId, newMemberUserId))
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.NOT_FOUND_GATHERING);
    }
}