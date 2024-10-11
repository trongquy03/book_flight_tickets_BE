package com.springjpa.demoJPA.mapper;

import com.springjpa.demoJPA.dto.request.UserCreationRequest;
import com.springjpa.demoJPA.dto.request.UserUpdateRequest;
import com.springjpa.demoJPA.dto.response.UserResponse;
import com.springjpa.demoJPA.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
    UserResponse toUserResponse(User user);
}
