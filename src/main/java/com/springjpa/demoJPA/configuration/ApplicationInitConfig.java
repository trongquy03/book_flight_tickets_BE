package com.springjpa.demoJPA.configuration;

import com.springjpa.demoJPA.entity.User;
import com.springjpa.demoJPA.enums.Role;
import com.springjpa.demoJPA.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {


    PasswordEncoder passwordEncoder;
    @Bean
    @ConditionalOnProperty(prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "com.mysql.cj.jdbc.Driver"
    )
    ApplicationRunner applicationRunner(UserRepository userRepository){
        log.info("Initializing application runner ...");
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()){
                Set<Role> roles = new HashSet<>();
                roles.add(Role.ADMIN);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
             //           .roles(roles)
                        .build();
                userRepository.save(user);




                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
