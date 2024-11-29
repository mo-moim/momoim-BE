package com.triplem.momoim.core.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringFixture;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.RecruitStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("local")
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
        Gathering gathering = gatheringRepository.save(GatheringFixture.createGathering(1L, RecruitStatus.RECRUITING, 10, 0));
        Long newMemberUserId = 2L;

        //when
        gatheringMemberAppender.append(newMemberUserId, gathering.getId());

        // then
        Boolean isSuccessAppend = gatheringMemberRepository.isGatheringMember(newMemberUserId, gathering.getId());
        assertThat(isSuccessAppend).isTrue();
    }

    @Test
    @DisplayName("모집 중인 모임이 아니면 멤버를 추가 할 수 없다.")
    void cannotAppendWhenGatheringIsNotRecruiting() {
        //given
        Gathering notRecruitingGathering = gatheringRepository.save(GatheringFixture.createGathering(1L, RecruitStatus.STOP, 10, 0));
        Long newMemberUserId = 2L;

        //when then
        assertThatThrownBy(() -> gatheringMemberAppender.append(newMemberUserId, notRecruitingGathering.getId()))
            .hasMessageContaining("모집 중인 모임이 아닙니다.");

        Boolean isSuccessAppend = gatheringMemberRepository.isGatheringMember(newMemberUserId, notRecruitingGathering.getId());
        assertThat(isSuccessAppend).isFalse();
    }

    @Test
    @DisplayName("인원이 다 찬 모임은 멤버를 추가 할 수 없다.")
    void cannotAppendWhenIsFull() {
        //given
        Gathering fullGathering = gatheringRepository.save(GatheringFixture.createGathering(1L, RecruitStatus.RECRUITING, 10, 10));
        Long newMemberUserId = 2L;

        //when then
        assertThatThrownBy(() -> gatheringMemberAppender.append(newMemberUserId, fullGathering.getId()))
            .hasMessageContaining("인원이 다 찬 모임입니다.");

        Boolean isSuccessAppend = gatheringMemberRepository.isGatheringMember(newMemberUserId, fullGathering.getId());
        assertThat(isSuccessAppend).isFalse();
    }

    @Test
    @DisplayName("이미 모임에 참가중인 유저는 멤버로 추가 될 수 없다.")
    void cannotAppendWhenIsAlreadyMember() {
        //given
        Gathering gathering = gatheringRepository.save(GatheringFixture.createGathering(1L, RecruitStatus.RECRUITING, 10, 1));
        GatheringMember member1 = GatheringMember.create(2L, gathering.getId());
        gatheringMemberRepository.save(member1);

        //when then
        assertThatThrownBy(() -> gatheringMemberAppender.append(member1.getUserId(), gathering.getId()))
            .hasMessageContaining("이미 가입 한 모임입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 모임에 멤버가 추가 될 수 없다.")
    void cannotAppendWhenIsNotExistsGathering() {
        //given
        Long notExistsGatheringId = 123L;
        Long newMemberUserId = 2L;
        //when then
        assertThatThrownBy(() -> gatheringMemberAppender.append(notExistsGatheringId, newMemberUserId))
            .hasMessageContaining("존재하지 않는 모임입니다.");
    }
}