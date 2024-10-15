package com.springjpa.demoJPA.service.impl;

import com.springjpa.demoJPA.dto.request.PermissionRequest;
import com.springjpa.demoJPA.dto.response.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getAll();
    void delete(String id);

}
