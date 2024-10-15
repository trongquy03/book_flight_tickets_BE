package com.springjpa.demoJPA.service;

import com.springjpa.demoJPA.dto.request.RoleRequest;
import com.springjpa.demoJPA.dto.response.RoleResponse;
import com.springjpa.demoJPA.mapper.RoleMapper;
import com.springjpa.demoJPA.repository.PermissionRepository;
import com.springjpa.demoJPA.repository.RoleRepository;
import com.springjpa.demoJPA.service.impl.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService implements IRoleService {
    PermissionRepository permissionRepository;
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest roleRequest) {
        var role = roleMapper.toRole(roleRequest);
        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void delete(String id) {
        roleRepository.deleteById(id);
    }
}
