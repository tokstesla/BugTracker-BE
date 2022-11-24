package com.bugtracker.bugtracker;

import com.bugtracker.bugtracker.entity.Member;
import com.bugtracker.bugtracker.entity.enums.Role;
import com.bugtracker.bugtracker.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BugTrackerApplication {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(BugTrackerApplication.class, args);
    }

}
