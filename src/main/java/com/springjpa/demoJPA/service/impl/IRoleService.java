package com.springjpa.demoJPA.service.impl;

import com.springjpa.demoJPA.dto.request.RoleRequest;
import com.springjpa.demoJPA.dto.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse createRole(RoleRequest roleRequest);
    List<RoleResponse> getAll();
    void delete(String id);
}
