package com.springjpa.demoJPA.service;

import com.springjpa.demoJPA.dto.request.PermissionRequest;
import com.springjpa.demoJPA.dto.response.PermissionResponse;
import com.springjpa.demoJPA.entity.Permission;
import com.springjpa.demoJPA.mapper.PermissionMapper;
import com.springjpa.demoJPA.repository.PermissionRepository;
import com.springjpa.demoJPA.service.impl.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService implements IPermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);

    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String id) {
        permissionRepository.deleteById(id);
    }
}
