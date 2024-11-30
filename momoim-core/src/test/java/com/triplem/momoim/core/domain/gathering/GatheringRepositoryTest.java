package com.triplem.momoim.core.domain.gathering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        Gathering gathering = GatheringFixture.createGathering(1L, RecruitStatus.RECRUITING, 10, 0);

        //when
        Gathering savedGathering = gatheringRepository.save(gathering);

        //then
        assertThat(savedGathering)
            .isNotNull()
            .extracting(
                "managerId", "category", "subCategory", "recruitStatus",
                "name", "image", "description", "location",
                "capacity", "participantCount", "nextGatheringAt", "startAt",
                "endAt", "createdAt")
            .containsExactly(
                gathering.getManagerId(), gathering.getCategory(), gathering.getSubCategory(), gathering.getRecruitStatus(),
                gathering.getName(), gathering.getImage(), gathering.getDescription(), gathering.getLocation(),
                gathering.getCapacity(), gathering.getParticipantCount(), gathering.getNextGatheringAt(), gathering.getStartAt(),
                gathering.getEndAt(), gathering.getCreatedAt());

        assertThat(savedGathering.getTags()).isEqualTo(gathering.getTags());
    }

    @Test
    @DisplayName("gatheringId와 일치하는 모임을 조회할 수 있다.")
    void findGatheringById() {
        //given
        Gathering gathering = GatheringFixture.createGathering(1L, RecruitStatus.RECRUITING, 10, 0);
        Gathering savedGathering = gatheringRepository.save(gathering);

        //when
        Gathering foundGathering = gatheringRepository.findById(savedGathering.getId());

        //then
        assertThat(foundGathering.getId()).isEqualTo(savedGathering.getId());
    }

    @Test
    @DisplayName("존재하지 않는 id로 조회 시 예외가 발생한다.")
    void throwExceptionWhenFindWrongGatheringId() {
        //given
        Long wrongGatheringId = 1L;

        //when then
        assertThatThrownBy(() -> gatheringRepository.findById(wrongGatheringId));
    }

    @Test
    @DisplayName("카테고리 필터를 통해 모임을 조회 할 수 있다.")
    void findByGatheringSearchOptionCategory() {
        //given
        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering1")
                .image("image1")
                .description("description1")
                .tags(List.of("tag1"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(10)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("TRAVEL")
                .subCategory("FISHING")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering2")
                .image("image2")
                .description("description2")
                .tags(List.of("tag2"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(20)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .category("FOOD")
            .sortType(GatheringSearchSortType.GATHERING_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .build();

        //when
        List<Gathering> gatherings = gatheringRepository.findBySearchOption(gatheringSearchOption);

        //then
        assertThat(gatherings).hasSize(1);
        assertThat(gatherings.get(0).getCategory()).isEqualTo(gatheringSearchOption.getCategory());
    }

    @Test
    @DisplayName("서브 카테고리 필터를 통해 모임을 조회 할 수 있다.")
    void findByGatheringSearchOptionSubCategory() {
        //given
        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering1")
                .image("image1")
                .description("description1")
                .tags(List.of("tag1"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(10)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("RESTAURANT")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering2")
                .image("image2")
                .description("description2")
                .tags(List.of("tag2"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(20)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .subCategory("RESTAURANT")
            .sortType(GatheringSearchSortType.GATHERING_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .build();

        //when
        List<Gathering> gatherings = gatheringRepository.findBySearchOption(gatheringSearchOption);

        //then
        assertThat(gatherings).hasSize(1);
        assertThat(gatherings.get(0).getSubCategory()).isEqualTo(gatheringSearchOption.getSubCategory());
    }

    @Test
    @DisplayName("모임 위치 필터를 통해 모임을 조회 할 수 있다.")
    void findByGatheringSearchOptionLocation() {
        //given
        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering1")
                .image("image1")
                .description("description1")
                .tags(List.of("tag1"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(10)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering2")
                .image("image2")
                .description("description2")
                .tags(List.of("tag2"))
                .location(GatheringLocation.SEOUL)
                .capacity(100)
                .participantCount(20)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .location(GatheringLocation.INCHEON)
            .sortType(GatheringSearchSortType.GATHERING_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .build();

        //when
        List<Gathering> gatherings = gatheringRepository.findBySearchOption(gatheringSearchOption);

        //then
        assertThat(gatherings).hasSize(1);
        assertThat(gatherings.get(0).getLocation()).isEqualTo(gatheringSearchOption.getLocation());
    }

    @Test
    @DisplayName("모임 날짜 필터를 통해 모임을 조회 할 수 있다.")
    void findByGatheringSearchOptionGatheringDate() {
        //given
        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering1")
                .image("image1")
                .description("description1")
                .tags(List.of("tag1"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(10)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering2")
                .image("image2")
                .description("description2")
                .tags(List.of("tag2"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(20)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 10, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .gatheringDate(LocalDate.of(2024, 12, 10))
            .sortType(GatheringSearchSortType.GATHERING_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .build();

        //when
        List<Gathering> gatherings = gatheringRepository.findBySearchOption(gatheringSearchOption);

        //then
        assertThat(gatherings).hasSize(1);
    }

    @Test
    @DisplayName("방장 ID 필터를 통해 모임을 조회 할 수 있다.")
    void findByGatheringSearchOptionManagerId() {
        //given
        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering1")
                .image("image1")
                .description("description1")
                .tags(List.of("tag1"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(10)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        gatheringRepository.save(
            Gathering.builder()
                .managerId(8L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering2")
                .image("image2")
                .description("description2")
                .tags(List.of("tag2"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(20)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .managerId(5L)
            .sortType(GatheringSearchSortType.GATHERING_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .build();

        //when
        List<Gathering> gatherings = gatheringRepository.findBySearchOption(gatheringSearchOption);

        //then
        assertThat(gatherings).hasSize(1);
        assertThat(gatherings.get(0).getManagerId()).isEqualTo(gatheringSearchOption.getManagerId());
    }

    @Test
    @DisplayName("모임 날짜 순으로 정렬하여 모임을 조회할 수 있다.")
    void sortByGatheringAt() {
        //given
        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering1")
                .image("image1")
                .description("description1")
                .tags(List.of("tag1"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(10)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering2")
                .image("image2")
                .description("description2")
                .tags(List.of("tag2"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(20)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 30, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .sortType(GatheringSearchSortType.GATHERING_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .build();

        //when
        List<Gathering> gatherings = gatheringRepository.findBySearchOption(gatheringSearchOption);

        //then
        assertThat(gatherings).hasSize(2);
        assertThat(gatherings.get(0).getNextGatheringAt()).isBefore(gatherings.get(1).getNextGatheringAt());
    }

    @Test
    @DisplayName("모집 마감 날짜 순으로 정렬하여 모임을 조회할 수 있다.")
    void sortByEndAt() {
        //given
        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering1")
                .image("image1")
                .description("description1")
                .tags(List.of("tag1"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(10)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2025, 12, 10, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering2")
                .image("image2")
                .description("description2")
                .tags(List.of("tag2"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(20)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 30, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2025, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .sortType(GatheringSearchSortType.END_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .build();

        //when
        List<Gathering> gatherings = gatheringRepository.findBySearchOption(gatheringSearchOption);

        //then
        assertThat(gatherings).hasSize(2);
        assertThat(gatherings.get(0).getEndAt()).isBefore(gatherings.get(1).getEndAt());
    }

    @Test
    @DisplayName("참여 인원 순으로 정렬하여 모임을 조회할 수 있다.")
    void sortByParticipantCount() {
        //given
        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering1")
                .image("image1")
                .description("description1")
                .tags(List.of("tag1"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(10)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2025, 12, 10, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("FOOD")
                .subCategory("COOK")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering2")
                .image("image2")
                .description("description2")
                .tags(List.of("tag2"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(20)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 30, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2025, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .sortType(GatheringSearchSortType.PARTICIPANT_COUNT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .build();

        //when
        List<Gathering> gatherings = gatheringRepository.findBySearchOption(gatheringSearchOption);

        //then
        assertThat(gatherings).hasSize(2);
        assertThat(gatherings.get(0).getParticipantCount()).isLessThan(gatherings.get(1).getParticipantCount());
    }
}