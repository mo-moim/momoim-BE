package com.triplem.momoim.core.domain.schedule.infrastructure;

import static com.triplem.momoim.core.domain.gathering.infrastructure.QGatheringEntity.gatheringEntity;
import static com.triplem.momoim.core.domain.member.infrastructure.QGatheringMemberEntity.gatheringMemberEntity;
import static com.triplem.momoim.core.domain.wishlist.infrastructure.QWishlistEntity.wishlistEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triplem.momoim.core.domain.gathering.enums.GatheringStatus;
import com.triplem.momoim.core.domain.schedule.dto.GatheringSchedule;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GatheringScheduleRepositoryImpl implements GatheringScheduleRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<GatheringSchedule> getSchedules(Long userId, int year) {
        return jpaQueryFactory.select(
                Projections.constructor(
                    GatheringSchedule.class,
                    gatheringEntity.id,
                    gatheringEntity.name,
                    gatheringEntity.image,
                    gatheringEntity.location,
                    gatheringEntity.category,
                    gatheringEntity.subCategory,
                    gatheringEntity.nextGatheringAt,
                    wishlistEntity.id.isNotNull()
                )
            )
            .from(gatheringMemberEntity)
            .where(gatheringMemberEntity.userId.eq(userId))
            .innerJoin(gatheringEntity).on(
                gatheringEntity.id.eq(gatheringMemberEntity.gatheringId),
                gatheringEntity.status.ne(GatheringStatus.CANCELED),
                gatheringEntity.nextGatheringAt.between(
                    LocalDateTime.of(year, 1, 1, 0, 0),
                    LocalDateTime.of(year, 12, 31, 23, 59, 59)
                )
            )
            .leftJoin(wishlistEntity).on(wishlistEntity.gatheringId.eq(gatheringEntity.id), wishlistEntity.userId.eq(userId))
            .orderBy(gatheringEntity.nextGatheringAt.asc())
            .fetch();
    }
}
