package com.springjpa.demoJPA.service;

import com.nimbusds.jose.JOSEException;
import com.springjpa.demoJPA.dto.request.AuthenticationRequest;
import com.springjpa.demoJPA.dto.request.IntrospectRequest;
import com.springjpa.demoJPA.dto.request.PermissionRequest;
import com.springjpa.demoJPA.dto.response.AuthenticationResponse;
import com.springjpa.demoJPA.dto.response.IntrospectResponse;
import com.springjpa.demoJPA.dto.response.PermissionResponse;

import java.text.ParseException;
import java.util.List;

public interface IPermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getAll();
    void delete(String id);

}
