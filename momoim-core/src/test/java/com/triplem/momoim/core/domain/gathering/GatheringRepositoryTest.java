package com.triplem.momoim.core.domain.gathering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.common.SortOrder;
import com.triplem.momoim.core.domain.member.GatheringMember;
import com.triplem.momoim.core.domain.member.GatheringMemberRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
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
                "name", "gatheringType", "image", "description", "location",
                "capacity", "participantCount", "nextGatheringAt", "createdAt",
                "isPeriodic", "status", "address")
            .containsExactly(
                gathering.getManagerId(), gathering.getCategory(), gathering.getSubCategory(),
                gathering.getName(), gathering.getGatheringType(), gathering.getImage(), gathering.getDescription(), gathering.getLocation(),
                gathering.getCapacity(), gathering.getParticipantCount(), gathering.getNextGatheringAt(), gathering.getCreatedAt(),
                gathering.getIsPeriodic(), gathering.getStatus(), gathering.getAddress());

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
            .paginationInformation(new PaginationInformation(0, 10))
            .sortType(GatheringSortType.UPDATE_AT)
            .sortOrder(SortOrder.ASC)
            .build();

        //when
        List<GatheringPreview> gatherings = gatheringRepository.searchGatherings(gatheringSearchOption);

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
            .paginationInformation(new PaginationInformation(0, 10))
            .sortType(GatheringSortType.UPDATE_AT)
            .sortOrder(SortOrder.ASC)
            .build();

        //when
        List<GatheringPreview> gatherings = gatheringRepository.searchGatherings(gatheringSearchOption);

        //then
        assertThat(gatherings).hasSize(1);
        assertThat(gatherings.get(0).getSubCategory()).isEqualTo(gatheringSearchOption.getSubCategory());
    }

    @Test
    @DisplayName("모임 목록 조회 시 페이징 처리를 할 수 있다.")
    void gatheringSearchWithPagination() {
        //given
        int offset = 0;
        int limit = 2;
        GatheringSearchOption gatheringSearchOption = GatheringSearchOption.builder()
            .sortType(GatheringSortType.UPDATE_AT)
            .sortOrder(SortOrder.ASC)
            .paginationInformation(new PaginationInformation(offset, limit))
            .build();

        gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        gatheringRepository.save(GatheringBuilder.builder().build().toGathering());
        gatheringRepository.save(GatheringBuilder.builder().build().toGathering());

        //when
        List<GatheringPreview> gatherings = gatheringRepository.searchGatherings(gatheringSearchOption);

        //then
        assertThat(gatherings).hasSize(2);
    }

    @Test
    @DisplayName("내가 참여한 모임을 조회 할 수 있다.")
    void getMyGatherings() {
        //given
        Long userId = 1L;
        Boolean isOnlyIMade = false;
        int offset = 0;
        int limit = 10;
        PaginationInformation paginationInformation = new PaginationInformation(offset, limit);

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
        List<GatheringPreview> gatherings = gatheringRepository.getMyGatherings(
            userId,
            new MyGatheringOption(isOnlyIMade, new PaginationInformation(offset, limit))
        );

        //then
        assertThat(gatherings).hasSize(5)
            .extracting("gatheringId")
            .contains(gathering1.getId(), gathering2.getId(), gathering3.getId(), gathering4.getId(), gathering5.getId());
    }
}