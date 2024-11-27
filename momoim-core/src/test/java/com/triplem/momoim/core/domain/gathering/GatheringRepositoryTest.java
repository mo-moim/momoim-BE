package com.triplem.momoim.core.domain.gathering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
class GatheringRepositoryTest {
    @Autowired
    private GatheringRepository gatheringRepository;

    @Test
    @DisplayName("Gathering 도메인 데이터를 DB에 저장한다.")
    void saveGathering() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Gathering gathering = Gathering.builder()
            .managerId(1L)
            .category("FOOD")
            .subCategory("COOK")
            .recruitStatus(RecruitStatus.RECRUITING)
            .name("요리 모임")
            .image("https://placehold.co/600x400")
            .capacity(10)
            .participantCount(0)
            .startAt(LocalDateTime.of(2024, 1, 1, 0, 0))
            .endAt(LocalDateTime.of(2024, 12, 31, 23, 59))
            .createdAt(now)
            .build();

        //when
        Gathering savedGathering = gatheringRepository.save(gathering);

        //then
        assertThat(savedGathering)
            .isNotNull()
            .extracting(
                "managerId", "category", "subCategory", "recruitStatus",
                "name", "image", "capacity", "participantCount",
                "startAt", "endAt", "createdAt")
            .containsExactly(
                gathering.getManagerId(), gathering.getCategory(), gathering.getSubCategory(), gathering.getRecruitStatus(),
                gathering.getName(), gathering.getImage(), gathering.getCapacity(), gathering.getParticipantCount(),
                gathering.getStartAt(), gathering.getEndAt(), gathering.getCreatedAt());
    }

    @Test
    @DisplayName("gatheringId와 일치하는 모임을 조회할 수 있다.")
    void findGatheringById() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Gathering gathering = Gathering.builder()
            .managerId(1L)
            .category("FOOD")
            .subCategory("COOK")
            .recruitStatus(RecruitStatus.RECRUITING)
            .name("요리 모임")
            .image("https://placehold.co/600x400")
            .capacity(10)
            .participantCount(0)
            .startAt(LocalDateTime.of(2024, 1, 1, 0, 0))
            .endAt(LocalDateTime.of(2024, 12, 31, 23, 59))
            .createdAt(now)
            .build();
        Gathering savedGathering = gatheringRepository.save(gathering);

        //when
        Gathering foundGathering = gatheringRepository.findById(savedGathering.getId());

        //then
        assertThat(foundGathering.getId()).isEqualTo(savedGathering.getId());
    }

    @Test
    @DisplayName("존재하지 않는 id로 조회 시 예외가 발생한다.")
    void test() {
        //given
        Long wrongGatheringId = 1L;

        //when then
        assertThatThrownBy(() -> gatheringRepository.findById(wrongGatheringId));
    }
}