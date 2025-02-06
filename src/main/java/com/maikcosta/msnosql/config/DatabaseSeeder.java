package com.maikcosta.msnosql.config;

import com.maikcosta.msnosql.domain.Post;
import com.maikcosta.msnosql.domain.User;
import com.maikcosta.msnosql.dto.AuthorDTO;
import com.maikcosta.msnosql.repository.PostRepository;
import com.maikcosta.msnosql.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabaseUser(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User u1 = new User(null, "João Silva", "joao@email.com");
                User u2 = new User(null, "Maria Souza", "maria@email.com");
                userRepository.saveAll(Arrays.asList(u1, u2));
                System.out.println("Usuários inseridos no banco!");
            }
        };
    }
    @Bean
    CommandLineRunner initDatabasePost(PostRepository postRepository) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return args -> {
            if (postRepository.count() == 0) {
                Post p1 = new Post(null,simpleDateFormat.parse("02/02/2025"),"Título Teste","Mensagem teste", new AuthorDTO(new User(null,"João","joao@email.com")));
                Post p2 = new Post(null,simpleDateFormat.parse("03/02/2025"),"Título Teste 1","Mensagem teste 1",new AuthorDTO(new User(null,"Maria","maria@email.com")));
                postRepository.saveAll(Arrays.asList(p1, p2));
                System.out.println("Post inseridos no banco!");
            }
        };
    }
}
