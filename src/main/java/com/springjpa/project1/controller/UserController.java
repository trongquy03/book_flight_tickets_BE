package com.springjpa.project1.controller;

import com.springjpa.project1.dto.request.ApiResponse;
import com.springjpa.project1.dto.request.UserCreationRequest;
import com.springjpa.project1.dto.request.UserUpdateRequest;
import com.springjpa.project1.dto.response.UserResponse;
import com.springjpa.project1.entity.User;
import com.springjpa.project1.service.UserService;
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
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.createUser(request));
        return response;
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username : {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUser())
                .build();
    }

    @GetMapping("/{userid}")
    UserResponse getUserById(@PathVariable Long userid) {
        return userService.getUserById(userid);
    }

    @PutMapping("/{userid}")
    UserResponse updateUser(@RequestBody UserUpdateRequest user, @PathVariable Long userid) {
        return userService.updateUser(userid, user);
    }

    @DeleteMapping("/{userid}")
    User deleteUser(@PathVariable Long userid) {
        userService.deleteUser(userid);
        return null;
    }
}