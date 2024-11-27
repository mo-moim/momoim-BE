package com.triplem.momoim.core.domain.gathering;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GatheringTest {
    @Test
    @DisplayName("isRecruiting 메서드는 모임이 모집중인 상태라면 True를 반환한다.")
    void isRecruiting_returnsTrueWhenGatheringIsRecruiting() {
        //given
        Gathering gathering = Gathering.builder()
            .id(1L)
            .recruitStatus(RecruitStatus.RECRUITING)
            .build();

        //when
        Boolean isRecruiting = gathering.isRecruiting();

        //then
        assertThat(isRecruiting).isTrue();
    }

    @Test
    @DisplayName("isRecruiting 메서드는 모임이 모집중인 상태가 아니라면 False를 반환한다.")
    void isRecruiting_returnsFalseWhenGatheringIsNotRecruiting() {
        //given
        Gathering gathering = Gathering.builder()
            .id(1L)
            .recruitStatus(RecruitStatus.STOP)
            .build();

        //when
        Boolean isRecruiting = gathering.isRecruiting();

        //then
        assertThat(isRecruiting).isFalse();
    }

    @Test
    @DisplayName("isFull 메서드는 모집 인원이 다 찼을 경우 True를 반환한다.")
    void isFull_returnsTrueWhenParticipantCountIsFull() {
        //given
        Gathering gathering = Gathering.builder()
            .id(1L)
            .capacity(10)
            .participantCount(10)
            .build();

        //when
        Boolean isFull = gathering.isFull();

        //then
        assertThat(isFull).isTrue();
    }

    @Test
    @DisplayName("isFull 메서드는 모집 인원에 여유가 있을 경우 False를 반환한다.")
    void isFull_returnsFalseWhenParticipantCountIsNotFull() {
        //given
        Gathering gathering = Gathering.builder()
            .id(1L)
            .capacity(10)
            .participantCount(5)
            .build();

        //when
        Boolean isFull = gathering.isFull();

        //then
        assertThat(isFull).isFalse();
    }
}