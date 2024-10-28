package com.springjpa.demoJPA.service.impl;

import com.springjpa.demoJPA.dto.request.UserCreationRequest;
import com.springjpa.demoJPA.dto.request.UserUpdateRequest;
import com.springjpa.demoJPA.dto.response.UserResponse;
import com.springjpa.demoJPA.entity.User;

import java.util.List;

public interface IUserService {
    UserResponse createUser(UserCreationRequest request);
    List<UserResponse> getUsers();
    UserResponse getUser(Long id);
    UserResponse getMyInfo();
    UserResponse updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);

}
