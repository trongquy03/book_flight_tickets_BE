package com.springjpa.project1.mapper;

import com.springjpa.project1.dto.request.UserCreationRequest;
import com.springjpa.project1.dto.request.UserUpdateRequest;
import com.springjpa.project1.dto.response.UserResponse;
import com.springjpa.project1.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
    UserResponse toUserResponse(User user);
}
