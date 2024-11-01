package com.springjpa.demoJPA.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springjpa.demoJPA.dto.request.UserCreationRequest;
import com.springjpa.demoJPA.dto.response.UserResponse;
import com.springjpa.demoJPA.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerIntegrationTest {
    @Container
    static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest");

    @DynamicPropertySource
    static void configureDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", ()-> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", ()-> "update");
    }

    @Autowired
    private MockMvc mvc;
    private UserCreationRequest request;
    private UserResponse userResponse;
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
    }

    @Test
    //
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String content = mapper.writeValueAsString(request);

        // WHEN, THEN
        var response = mvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("result.username").value("john"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.firstName").value("quy"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.lastName").value("quy"))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0)
        );
        log.info("Result: {}",response.andReturn().getResponse().getContentAsString());
    }
}
