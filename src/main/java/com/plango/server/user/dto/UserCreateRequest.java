package com.plango.server.user.dto;

public record UserCreateRequest(
        String nickname, //사용자 닉네임
        String mbti //사용자 MBTI
) {}
