package com.example.socinet;

import com.example.socinet.entity.Role;
import com.example.socinet.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SocinetApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocinetApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RoleRepository roleRepo) {
        return arg -> {
            List<Role> roles = roleRepo.findAll();
            if (!roles.isEmpty()) return;
            roles.add(new Role("USER"));
            roles.add(new Role("ADMIN"));
            roleRepo.saveAll(roles);
        };
    }
}
