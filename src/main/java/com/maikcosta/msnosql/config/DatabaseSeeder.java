package com.maikcosta.msnosql.config;

import com.maikcosta.msnosql.domain.User;
import com.maikcosta.msnosql.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User u1 = new User(null, "João Silva", "joao@email.com");
                User u2 = new User(null, "Maria Souza", "maria@email.com");
                userRepository.saveAll(Arrays.asList(u1, u2));
                System.out.println("Usuários inseridos no banco!");
            }
        };
    }
}
