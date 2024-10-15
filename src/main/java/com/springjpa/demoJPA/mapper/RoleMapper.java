package com.springjpa.demoJPA.mapper;

import com.springjpa.demoJPA.dto.request.PermissionRequest;
import com.springjpa.demoJPA.dto.request.RoleRequest;
import com.springjpa.demoJPA.dto.response.PermissionResponse;
import com.springjpa.demoJPA.dto.response.RoleResponse;
import com.springjpa.demoJPA.entity.Permission;
import com.springjpa.demoJPA.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);
    RoleResponse toRoleResponse(Role role);
}
