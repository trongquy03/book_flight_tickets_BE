package com.springjpa.demoJPA.controller;

import com.springjpa.demoJPA.dto.request.ApiResponse;
import com.springjpa.demoJPA.dto.request.UserCreationRequest;
import com.springjpa.demoJPA.dto.request.UserUpdateRequest;
import com.springjpa.demoJPA.dto.response.UserResponse;
import com.springjpa.demoJPA.entity.User;
import com.springjpa.demoJPA.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username : {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{myInfo}")
    ApiResponse<UserResponse> getMyinfor() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userid}")
    ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequest user, @PathVariable Long userid) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userid, user))
                .build();
    }

    @DeleteMapping("/{userid}")
    User deleteUser(@PathVariable Long userid) {
        userService.deleteUser(userid);
        return null;
    }
}
