package com.plango.server.travel.repos;

import com.plango.server.travel.entity.TravelCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelCourseRepository extends JpaRepository<TravelCourseEntity, Integer> {
}
