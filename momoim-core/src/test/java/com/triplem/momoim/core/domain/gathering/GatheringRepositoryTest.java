package com.triplem.momoim.core.domain.gathering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.triplem.momoim.core.domain.member.GatheringMember;
import com.triplem.momoim.core.domain.member.GatheringMemberRepository;
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

    @Autowired
    private GatheringMemberRepository gatheringMemberRepository;

    @Test
    @DisplayName("Gathering 도메인 데이터를 DB에 저장한다.")
    void saveGathering() {
        //given
        Gathering gathering = GatheringBuilder.builder().build().toGathering();

        //when
        Gathering savedGathering = gatheringRepository.save(gathering);

        //then
        assertThat(savedGathering)
            .isNotNull()
            .extracting(
                "managerId", "category", "subCategory",
                "name", "image", "description", "location",
                "capacity", "participantCount", "nextGatheringAt", "startAt",
                "endAt", "createdAt")
            .containsExactly(
                gathering.getManagerId(), gathering.getCategory(), gathering.getSubCategory(),
                gathering.getName(), gathering.getImage(), gathering.getDescription(), gathering.getLocation(),
                gathering.getCapacity(), gathering.getParticipantCount(), gathering.getNextGatheringAt(), gathering.getStartAt(),
                gathering.getEndAt(), gathering.getCreatedAt());

        assertThat(savedGathering.getTags()).isEqualTo(gathering.getTags());
    }

    @Test
    @DisplayName("gatheringId와 일치하는 모임을 조회할 수 있다.")
    void findGatheringById() {
        //given
        Gathering gathering = GatheringBuilder.builder().build().toGathering();
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
        String targetCategory = "FOOD";
        String anotherCategory = "TRAVEL";

        gatheringRepository.save(
            GatheringBuilder
                .builder()
                .category(targetCategory)
                .build()
                .toGathering()
        );

        gatheringRepository.save(
            GatheringBuilder.builder()
                .category(anotherCategory)
                .build()
                .toGathering()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .category(targetCategory)
            .sortType(GatheringSearchSortType.GATHERING_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .offset(0)
            .limit(10)
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
        String targetSubCategory = "COOK";
        String anotherSubCategory = "FISHING";

        gatheringRepository.save(
            GatheringBuilder.builder()
                .subCategory(targetSubCategory)
                .build()
                .toGathering()
        );

        gatheringRepository.save(
            GatheringBuilder.builder()
                .subCategory(anotherSubCategory)
                .build()
                .toGathering()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .subCategory(targetSubCategory)
            .sortType(GatheringSearchSortType.GATHERING_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .offset(0)
            .limit(10)
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
        GatheringLocation targetLocation = GatheringLocation.INCHEON;
        GatheringLocation anotherLocation = GatheringLocation.SEOUL;

        gatheringRepository.save(
            GatheringBuilder.builder()
                .location(targetLocation)
                .build()
                .toGathering()
        );

        gatheringRepository.save(
            GatheringBuilder.builder()
                .location(anotherLocation)
                .build()
                .toGathering()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .location(targetLocation)
            .sortType(GatheringSearchSortType.GATHERING_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .offset(0)
            .limit(10)
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
        LocalDate targetGatheringDate = LocalDate.of(2024, 12, 10);
        LocalDate anotherGatheringDate = LocalDate.of(2024, 12, 12);
        gatheringRepository.save(
            GatheringBuilder
                .builder()
                .nextGatheringAt(targetGatheringDate.atTime(10, 0, 0))
                .build()
                .toGathering()
        );

        gatheringRepository.save(
            GatheringBuilder
                .builder()
                .nextGatheringAt(anotherGatheringDate.atTime(10, 0, 0))
                .build()
                .toGathering()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .gatheringDate(targetGatheringDate)
            .sortType(GatheringSearchSortType.GATHERING_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .offset(0)
            .limit(10)
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
        Long targetManagerId = 1L;
        Long anotherManagerId = 2L;
        gatheringRepository.save(
            GatheringBuilder
                .builder()
                .managerId(targetManagerId)
                .build()
                .toGathering()
        );

        gatheringRepository.save(
            GatheringBuilder
                .builder()
                .managerId(anotherManagerId)
                .build()
                .toGathering()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .managerId(targetManagerId)
            .sortType(GatheringSearchSortType.GATHERING_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .offset(0)
            .limit(10)
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
            GatheringBuilder
                .builder()
                .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 10, 0, 0))
                .build()
                .toGathering()
        );

        gatheringRepository.save(
            GatheringBuilder
                .builder()
                .nextGatheringAt(LocalDateTime.of(2024, 12, 30, 10, 0, 0))
                .build()
                .toGathering()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .sortType(GatheringSearchSortType.GATHERING_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .offset(0)
            .limit(10)
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
            GatheringBuilder
                .builder()
                .endAt(LocalDateTime.of(2025, 12, 10, 23, 59, 59))
                .build()
                .toGathering()
        );

        gatheringRepository.save(
            GatheringBuilder
                .builder()
                .endAt(LocalDateTime.of(2025, 12, 31, 23, 59, 59))
                .build()
                .toGathering()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .sortType(GatheringSearchSortType.END_AT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .offset(0)
            .limit(10)
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
            GatheringBuilder
                .builder()
                .participantCount(10)
                .build()
                .toGathering()
        );

        gatheringRepository.save(
            GatheringBuilder
                .builder()
                .participantCount(20)
                .build()
                .toGathering()
        );

        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .sortType(GatheringSearchSortType.PARTICIPANT_COUNT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .offset(0)
            .limit(10)
            .build();

        //when
        List<Gathering> gatherings = gatheringRepository.findBySearchOption(gatheringSearchOption);

        //then
        assertThat(gatherings).hasSize(2);
        assertThat(gatherings.get(0).getParticipantCount()).isLessThan(gatherings.get(1).getParticipantCount());
    }

    @Test
    @DisplayName("모임 목록 조회 시 페이징 처리를 할 수 있다.")
    void gatheringSearchWithPagination() {
        //given
        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .sortType(GatheringSearchSortType.PARTICIPANT_COUNT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .offset(0)
            .limit(2)
            .build();

        Gathering gathering1 = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        Gathering gathering2 = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        Gathering gathering3 = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        Gathering gathering4 = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());

        //when
        List<Gathering> gatherings = gatheringRepository.findBySearchOption(gatheringSearchOption);

        //then
        assertThat(gatherings).hasSize(2);
        assertThat(gatherings.get(0).getId()).isEqualTo(gathering1.getId());
        assertThat(gatherings.get(1).getId()).isEqualTo(gathering2.getId());
    }

    @Test
    @DisplayName("내가 참여한 모임을 조회 할 수 있다.")
    void getMyGatherings() {
        //given
        Long userId = 1L;
        int offset = 0;
        int size = 10;

        Gathering gathering1 = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        Gathering gathering2 = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        Gathering gathering3 = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        Gathering gathering4 = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        Gathering gathering5 = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());

        gatheringMemberRepository.save(GatheringMember.create(userId, gathering1.getId()));
        gatheringMemberRepository.save(GatheringMember.create(userId, gathering2.getId()));
        gatheringMemberRepository.save(GatheringMember.create(userId, gathering3.getId()));
        gatheringMemberRepository.save(GatheringMember.create(userId, gathering4.getId()));
        gatheringMemberRepository.save(GatheringMember.create(userId, gathering5.getId()));

        //when
        List<Gathering> gatherings = gatheringRepository.getMyGatherings(userId, offset, size);

        //then
        assertThat(gatherings).hasSize(5)
            .extracting("id")
            .contains(gathering1.getId(), gathering2.getId(), gathering3.getId(), gathering4.getId(), gathering5.getId());
    }
}