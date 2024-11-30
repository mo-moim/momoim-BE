package com.triplem.momoim.api.gathering;

import static org.assertj.core.api.Assertions.assertThat;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.GatheringSearchSortOrder;
import com.triplem.momoim.core.domain.gathering.GatheringSearchSortType;
import com.triplem.momoim.core.domain.gathering.RecruitStatus;
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
class GatheringServiceTest {
    @Autowired
    private GatheringService gatheringService;

    @Autowired
    private GatheringRepository gatheringRepository;

    @Test
    @DisplayName("검색 옵션을 통해 모임 목록을 조회할 수 있다.")
    void searchGatheringByGatheringSearchOption() {
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

        gatheringRepository.save(
            Gathering.builder()
                .managerId(5L)
                .category("TRAVEL")
                .subCategory("FISHING")
                .recruitStatus(RecruitStatus.RECRUITING)
                .name("gathering3")
                .image("image2")
                .description("description3")
                .tags(List.of("tag3"))
                .location(GatheringLocation.INCHEON)
                .capacity(100)
                .participantCount(20)
                .nextGatheringAt(LocalDateTime.of(2024, 12, 30, 10, 0, 0))
                .startAt(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endAt(LocalDateTime.of(2025, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build()
        );

        GatheringSearchOption searchOption = GatheringSearchOption.builder()
            .category("FOOD")
            .subCategory("COOK")
            .sortType(GatheringSearchSortType.PARTICIPANT_COUNT)
            .sortOrder(GatheringSearchSortOrder.ASC)
            .build();

        //when
        List<GatheringSearchItem> gatheringSearchItems = gatheringService.searchGathering(searchOption);

        //then
        assertThat(gatheringSearchItems).hasSize(2);

        assertThat(gatheringSearchItems.get(0).getName()).isEqualTo("gathering1");
        assertThat(gatheringSearchItems.get(1).getName()).isEqualTo("gathering2");
    }
}