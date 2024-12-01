package com.triplem.momoim.core.domain.gathering;

import static com.triplem.momoim.core.domain.gathering.QGatheringEntity.gatheringEntity;
import static com.triplem.momoim.core.domain.member.QGatheringMemberEntity.gatheringMemberEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triplem.momoim.core.common.PaginationInformation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GatheringRepositoryImpl implements GatheringRepository {
    private final GatheringJpaRepository gatheringJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Gathering save(Gathering gathering) {
        return gatheringJpaRepository.save(GatheringEntity.from(gathering))
            .toModel();
    }

    @Override
    public Gathering findById(Long id) {
        return gatheringJpaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 모임입니다."))
            .toModel();
    }

    @Override
    public List<Gathering> findBySearchOption(GatheringSearchOption searchOption) {
        return jpaQueryFactory.select(gatheringEntity)
            .from(gatheringEntity)
            .where(whereGatheringSearchOption(searchOption))
            .orderBy(sortGatheringSearch(searchOption.getSortType(), searchOption.getSortOrder()))
            .limit(searchOption.getLimit())
            .offset(searchOption.getOffset())
            .fetch()
            .stream()
            .map(GatheringEntity::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Gathering> getMyGatherings(Long userId, PaginationInformation paginationInformation) {
        return jpaQueryFactory.select(gatheringEntity)
            .from(gatheringMemberEntity)
            .where(gatheringMemberEntity.userId.eq(userId))
            .offset(paginationInformation.getOffset())
            .limit(paginationInformation.getLimit())
            .orderBy(gatheringEntity.id.desc())
            .innerJoin(gatheringEntity).on(gatheringEntity.id.eq(gatheringMemberEntity.gatheringId))
            .fetch()
            .stream()
            .map(GatheringEntity::toModel)
            .collect(Collectors.toList());
    }

    private BooleanBuilder whereGatheringSearchOption(GatheringSearchOption searchOption) {
        BooleanBuilder builder = new BooleanBuilder();
        if (searchOption.getGatheringIds() != null && !searchOption.getGatheringIds().isEmpty()) {
            builder.and(gatheringEntity.id.in(searchOption.getGatheringIds()));
        }

        if (searchOption.getCategory() != null) {
            builder.and(gatheringEntity.category.eq(searchOption.getCategory()));
        }

        if (searchOption.getSubCategory() != null) {
            builder.and(gatheringEntity.subCategory.eq(searchOption.getSubCategory()));
        }

        if (searchOption.getLocation() != null) {
            builder.and(gatheringEntity.location.eq(searchOption.getLocation()));
        }

        if (searchOption.getGatheringDate() != null) {
            builder.and(gatheringEntity.nextGatheringAt.between(
                searchOption.getGatheringDate().atTime(0, 0, 0),
                searchOption.getGatheringDate().atTime(23, 59, 59))
            );
        }

        if (searchOption.getManagerId() != null) {
            builder.and(gatheringEntity.managerId.eq(searchOption.getManagerId()));
        }

        return builder;
    }

    private OrderSpecifier<?> sortGatheringSearch(GatheringSearchSortType sort, GatheringSearchSortOrder sortOrder) {
        switch (sort) {
            case GATHERING_AT -> {
                return sortOrder.equals(GatheringSearchSortOrder.ASC) ? gatheringEntity.nextGatheringAt.asc()
                    : gatheringEntity.nextGatheringAt.desc();
            }
            case END_AT -> {
                return sortOrder.equals(GatheringSearchSortOrder.ASC) ? gatheringEntity.endAt.asc() : gatheringEntity.endAt.desc();
            }
            case PARTICIPANT_COUNT -> {
                return sortOrder.equals(GatheringSearchSortOrder.ASC) ? gatheringEntity.participantCount.asc() : gatheringEntity.endAt.desc();
            }
            default -> {
                return null;
            }
        }
    }
}
