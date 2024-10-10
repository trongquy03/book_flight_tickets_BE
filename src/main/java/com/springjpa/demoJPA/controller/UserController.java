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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.createUser(request));
        return response;
    }

    @GetMapping
    List<User> getAllUsers() {
        return userService.getUser();
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
