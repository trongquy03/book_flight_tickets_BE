package com.springjpa.demoJPA.service;

import com.springjpa.demoJPA.dto.request.PermissionRequest;
import com.springjpa.demoJPA.dto.request.UserCreationRequest;
import com.springjpa.demoJPA.dto.request.UserUpdateRequest;
import com.springjpa.demoJPA.dto.response.PermissionResponse;
import com.springjpa.demoJPA.dto.response.UserResponse;
import com.springjpa.demoJPA.entity.User;

import java.util.List;

public interface IUserService {
    User createUser(UserCreationRequest request);
    List<UserResponse> getUsers();
    UserResponse getUser(Long id);
    UserResponse getMyInfo();
    UserResponse updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);

}
