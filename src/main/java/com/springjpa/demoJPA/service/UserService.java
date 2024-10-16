package com.springjpa.demoJPA.service;

import com.springjpa.demoJPA.dto.request.UserCreationRequest;
import com.springjpa.demoJPA.dto.request.UserUpdateRequest;
import com.springjpa.demoJPA.dto.response.UserResponse;
import com.springjpa.demoJPA.entity.User;
import com.springjpa.demoJPA.enums.Role;
import com.springjpa.demoJPA.exception.AppException;
import com.springjpa.demoJPA.exception.ErrorCode;
import com.springjpa.demoJPA.mapper.UserMapper;
import com.springjpa.demoJPA.repository.RoleRepository;
import com.springjpa.demoJPA.repository.UserRepository;
import com.springjpa.demoJPA.service.impl.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    public User createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.USER);
      //  user.setRoles(roles);
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method getUsers");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(Long id) {
        log.info("In method getUser my Id");
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException(ErrorCode.USER_NOT_EXISTED.getMessage())));
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(Long id,UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
