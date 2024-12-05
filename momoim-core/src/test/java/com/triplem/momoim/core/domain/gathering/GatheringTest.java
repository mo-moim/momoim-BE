package com.triplem.momoim.core.domain.gathering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GatheringTest {
    @Test
    @DisplayName("모임 참여 검증 시 모집 중이지 않은 모임인 경우 예외를 발생시킨다.")
    void throwExceptionWhenGatheringIsNotRecruiting() {
        //given
        Gathering stopRecruitingGathering = GatheringBuilder
            .builder()
            .status(GatheringStatus.CLOSED)
            .build()
            .toGathering();

        Gathering deletedGathering = GatheringBuilder
            .builder()
            .status(GatheringStatus.CANCELED)
            .build()
            .toGathering();

        //when then
        assertThatThrownBy(stopRecruitingGathering::validateJoin)
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.NOT_RECRUITING_GATHERING);

        assertThatThrownBy(deletedGathering::validateJoin)
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.NOT_RECRUITING_GATHERING);
    }

    @Test
    @DisplayName("모임 참여 검증 시 인원이 다찬 경우 예외를 발생시킨다.")
    void throwExceptionWhenGatheringIsFull() {
        //given
        Gathering gathering = GatheringBuilder
            .builder()
            .capacity(10)
            .participantCount(10)
            .build()
            .toGathering();

        //when then
        assertThatThrownBy(gathering::validateJoin)
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("exceptionCode", ExceptionCode.FULL_PARTICIPANT_GATHERING);
    }

    @Test
    @DisplayName("모임을 취소 상태로 변경할 수 있다.")
    void cancel() {
        //given
        Long requesterId = 1L;

        Gathering gathering = GatheringBuilder.builder()
            .managerId(requesterId)
            .build()
            .toGathering();

        //when
        gathering.cancel();

        //then
        assertThat(gathering.getStatus()).isEqualTo(GatheringStatus.CANCELED);
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

    @Test
    @DisplayName("isRecruiting 메서드는 모집 중인 모임인 경우 True를 반환한다.")
    void isRecruiting_returnsTrueWhenStatusIsRecruiting() {
        //given
        Gathering gathering = GatheringBuilder
            .builder()
            .status(GatheringStatus.OPEN)
            .build()
            .toGathering();

        //when
        Boolean status = gathering.isRecruiting();

        //then
        assertThat(status).isTrue();
    }

    @Test
    @DisplayName("isRecruiting 메서드는 모집 중이지 않은 경우 False를 반환한다.")
    void isRecruiting_returnsTrueWhenStatusIsNotRecruiting() {
        //given
        Gathering stopRecruitingGathering = GatheringBuilder
            .builder()
            .status(GatheringStatus.CLOSED)
            .build()
            .toGathering();

        Gathering deletedGathering = GatheringBuilder
            .builder()
            .status(GatheringStatus.CANCELED)
            .build()
            .toGathering();

        //when
        Boolean stopRecruitingGatheringStatus = stopRecruitingGathering.isRecruiting();
        Boolean deletedGatheringStatus = deletedGathering.isRecruiting();

        //then
        assertThat(stopRecruitingGatheringStatus).isFalse();
        assertThat(deletedGatheringStatus).isFalse();
    }
}