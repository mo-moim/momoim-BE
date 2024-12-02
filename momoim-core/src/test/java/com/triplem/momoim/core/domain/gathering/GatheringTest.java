package com.triplem.momoim.core.domain.gathering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GatheringTest {
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
            .hasMessage("인원이 다 찬 모임입니다.");
    }

    @Test
    @DisplayName("모임 참여 검증 시 종료 된 모임인 경우 예외를 발생시킨다.")
    void throwExceptionWhenGatheringIsEnd() {
        //given
        Gathering endGathering = GatheringBuilder
            .builder()
            .endAt(LocalDateTime.now().minusDays(10))
            .build()
            .toGathering();

        //when then
        assertThatThrownBy(endGathering::validateJoin)
            .hasMessage("종료 된 모임입니다.");
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
        gathering.cancel(requesterId);

        //then
        assertThat(gathering.getIsCanceled()).isTrue();
    }

    @Test
    @DisplayName("모임 생성자가 아니면 모임을 취소 할 수 없다.")
    void cannotCancelWhenRequesterIsNotManager() {
        //given
        Long managerId = 1L;
        Long requesterId = 2L;
        Gathering gathering = GatheringBuilder.builder()
            .managerId(managerId)
            .build()
            .toGathering();

        //when then
        assertThatThrownBy(() -> gathering.cancel(requesterId))
            .hasMessage("모임을 취소할 권한이 없습니다.");

        assertThat(gathering.getIsCanceled()).isFalse();
    }

    @Test
    @DisplayName("이미 종료된 모임은 취소할 수 없다.")
    void cannotCancelEndGathering() {
        //given
        Long requesterId = 1L;
        Gathering gathering = GatheringBuilder.builder()
            .endAt(LocalDateTime.now().minusDays(10))
            .build()
            .toGathering();

        //when then
        assertThatThrownBy(() -> gathering.cancel(requesterId))
            .hasMessage("종료 된 모임입니다.");

        assertThat(gathering.getIsCanceled()).isFalse();
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
    @DisplayName("isEnd 메서드는 종료된 모임인 경우 True를 반환한다.")
    void isEnd_returnsTrueWhenGatheringIsEnd() {
        //given
        LocalDateTime past = LocalDateTime.now().minusDays(10);
        Gathering endGathering = GatheringBuilder.builder()
            .endAt(past)
            .build()
            .toGathering();

        //when
        Boolean isEnd = endGathering.isEnd();

        //then
        assertThat(isEnd).isTrue();
    }

    @Test
    @DisplayName("isEnd 메서드는 운영중인 모임인 경우 False를 반환한다.")
    void isEnd_returnsFalseWhenGatheringIsNotEnd() {
        //given
        LocalDateTime future = LocalDateTime.now().plusDays(10);
        Gathering endGathering = GatheringBuilder.builder()
            .endAt(future)
            .build()
            .toGathering();

        //when
        Boolean isEnd = endGathering.isEnd();

        //then
        assertThat(isEnd).isFalse();
    }
}