package com.plango.server.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// <엔티티, 해당 키의 타입>
// 기본적으로 대부분의 CRUD 연산 타입을 제공
// 사용자 쿼리만 여기에 메서드로 설정
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 서비스에서 publicId는 일관적으로 만들고 있으므로
    // 리포지터리에 추가함
    Optional<UserEntity> findByPublicId(String publicId);
}
