package com.springjpa.demoJPA.service.impl;

import com.nimbusds.jose.JOSEException;
import com.springjpa.demoJPA.dto.request.AuthenticationRequest;
import com.springjpa.demoJPA.dto.request.IntrospectRequest;
import com.springjpa.demoJPA.dto.request.LogoutRequest;
import com.springjpa.demoJPA.dto.response.AuthenticationResponse;
import com.springjpa.demoJPA.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface IAuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    void logout(LogoutRequest request) throws ParseException, JOSEException;

}
