package com.triplem.momoim.core.domain.gathering;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GatheringTest {
    @Test
    @DisplayName("isRecruiting 메서드는 모임이 모집중인 상태라면 True를 반환한다.")
    void isRecruiting_returnsTrueWhenGatheringIsRecruiting() {
        //given
        Gathering recruitingGathering = GatheringBuilder
            .builder()
            .recruitStatus(RecruitStatus.RECRUITING)
            .build()
            .toGathering();

        //when
        Boolean isRecruiting = recruitingGathering.isRecruiting();

        //then
        assertThat(isRecruiting).isTrue();
    }

    @Test
    @DisplayName("isRecruiting 메서드는 모임이 모집중인 상태가 아니라면 False를 반환한다.")
    void isRecruiting_returnsFalseWhenGatheringIsNotRecruiting() {
        //given
        Gathering stopGathering = GatheringBuilder
            .builder()
            .recruitStatus(RecruitStatus.STOP)
            .build()
            .toGathering();

        //when
        Boolean isRecruiting = stopGathering.isRecruiting();

        //then
        assertThat(isRecruiting).isFalse();
    }

    @Test
    @DisplayName("isFull 메서드는 모집 인원이 다 찼을 경우 True를 반환한다.")
    void isFull_returnsTrueWhenParticipantCountIsFull() {
        //given
        Gathering fullGathering = GatheringBuilder
            .builder()
            .capacity(10)
            .participantCount(10)
            .build()
            .toGathering();
        
        //when
        Boolean isFull = fullGathering.isFull();

        //then
        assertThat(isFull).isTrue();
    }

    @Test
    @DisplayName("isFull 메서드는 모집 인원에 여유가 있을 경우 False를 반환한다.")
    void isFull_returnsFalseWhenParticipantCountIsNotFull() {
        //given
        Gathering notFullGathering = GatheringBuilder.builder()
            .capacity(10)
            .participantCount(1)
            .build()
            .toGathering();

        //when
        Boolean isFull = notFullGathering.isFull();

        //then
        assertThat(isFull).isFalse();
    }
}