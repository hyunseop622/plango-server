CREATE TABLE `users` (  -- 유저는 예약어라 백틱으로 감싸야 안전
    user_id     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,  -- 내부 PK
    user_public_id  VARCHAR(36) NOT NULL, -- 공개키(UUID) UUID는 36자리
    user_nickname VARCHAR(15) NOT NULL,
    user_mbti     VARCHAR(4) NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE KEY uq_user_public_id (user_public_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
