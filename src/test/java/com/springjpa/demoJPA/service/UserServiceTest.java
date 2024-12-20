package com.springjpa.demoJPA.service;

import com.springjpa.demoJPA.dto.request.UserCreationRequest;
import com.springjpa.demoJPA.dto.response.UserResponse;
import com.springjpa.demoJPA.entity.User;
import com.springjpa.demoJPA.exception.AppException;
import com.springjpa.demoJPA.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate birthDate;

    @BeforeEach
    void initData(){
        birthDate = LocalDate.of(1990, 1, 1);

        request = UserCreationRequest.builder()
                .username("john")
                .firstName("quy")
                .lastName("quy")
                .password("quy123456")
                .birthDate(birthDate)
                .build();

        userResponse = UserResponse.builder()
                .id(5)
                .username("john")
                .firstName("quy")
                .lastName("quy")
                .birthDate(birthDate)
                .build();
        user = User.builder()
                .id(5)
                .username("john")
                .firstName("quy")
                .lastName("quy")
                .birthDate(birthDate)
                .build();
    }

    @Test
    void createUser_validRequest_success(){
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN

        var response = userService.createUser(request);

        // THEN
        assertThat(response.getId()).isEqualTo(5);
        assertThat(response.getUsername()).isEqualTo("john");
    }

    @Test
    void createUser_userExisted_fail(){
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN

        var exception = assertThrows(AppException.class,
                () -> userService.createUser(request));

        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_validRequest_success(){
        // GIVEN
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // WHEN

        var response = userService.getMyInfo();

        // THEN
        assertThat(response.getUsername()).isEqualTo("john");
        assertThat(response.getId()).isEqualTo(5);
    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_userNotFound_error(){
        // GIVEN
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        // WHEN

        var exception = assertThrows(AppException.class,
                () -> userService.getMyInfo());

        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1006);
    }
}
