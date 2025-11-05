package com.plango.server.user;

import com.plango.server.user.dto.UserCreateRequest;
import com.plango.server.user.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

@RestController //해당 어노테이션 붙으면 알아서 자동으로 JSON 직렬화 및 역직렬화함
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 임의 키 부여 -> DB 저장 -> 키 리턴
    // 유저 저장
    @PostMapping("/custom")
    public String registerUser(@RequestBody UserCreateRequest userCreateRequest) {
        String publicId = userService.createUser(userCreateRequest);
        return publicId;
    }

    // 유저 불러오기
    @GetMapping("/{publicId}")
    public UserResponse getUser(@PathVariable String publicId) {
        return userService.getUserByPublicId(publicId);
    }
}