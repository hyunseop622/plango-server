package com.plango.server.travel.repos;

import com.plango.server.travel.entity.TravelEntity;
import com.plango.server.user.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TravelRepository extends JpaRepository<TravelEntity, Long> {
    List<TravelEntity> findByUser(UserEntity user);

    // 앞으로 올 여행 (start > today) → 가까운 일정부터
    //유저 엔티티로 찾는데...
    // TravelStart를 기준으로 AFTER or BEFORE
    // ORDER BY TRAVELSTART ASC 오름차순
    List<TravelEntity> findByUserAndTravelStartAfterOrderByTravelStartAsc(
            UserEntity user, LocalDate today
    );

    // 이미 지난 여행 (end < today) → 최근에 다녀온 순서로
    List<TravelEntity> findByUserAndTravelEndBeforeOrderByTravelStartDesc(
            UserEntity user, LocalDate today
    );

    @EntityGraph(attributePaths = {"travelDays", "travelDays.courses"})
    Optional<TravelEntity> findByTravelId(Long travelId);

}
