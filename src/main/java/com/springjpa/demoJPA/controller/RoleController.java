package com.springjpa.demoJPA.controller;

import com.springjpa.demoJPA.dto.request.ApiResponse;
import com.springjpa.demoJPA.dto.request.RoleRequest;
import com.springjpa.demoJPA.dto.response.RoleResponse;
import com.springjpa.demoJPA.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/roles")
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(roleRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> findAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{roleid}")
    ApiResponse<Void> delete(@PathVariable String roleid) {
        roleService.delete(roleid);
        return ApiResponse.<Void>builder().build();
    }

}
