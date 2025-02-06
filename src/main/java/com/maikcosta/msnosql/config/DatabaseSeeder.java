package com.maikcosta.msnosql.config;

import com.maikcosta.msnosql.domain.Post;
import com.maikcosta.msnosql.domain.User;
import com.maikcosta.msnosql.dto.AuthorDTO;
import com.maikcosta.msnosql.repository.PostRepository;
import com.maikcosta.msnosql.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Configuration
public class DatabaseSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PostRepository postRepository) {
        return args -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            if (userRepository.count() == 0) {
                User joao = new User(null, "João Silva", "joao@email.com");
                User maria = new User(null, "Maria Souza", "maria@email.com");

                userRepository.saveAll(List.of(joao, maria));
                logger.info("Usuários inseridos no banco!");
            }

            User joao = userRepository.findByEmail("joao@email.com").orElse(null);
            User maria = userRepository.findByEmail("maria@email.com").orElse(null);

            if (joao == null || maria == null) {
                logger.warn("Usuários não encontrados! Pulando inserção de posts.");
                return;
            }

            if (postRepository.count() == 0) {
                try {
                    Post post1 = new Post(null, simpleDateFormat.parse("02/02/2025"), "Título Teste", "Mensagem teste", new AuthorDTO(joao));
                    Post post2 = new Post(null, simpleDateFormat.parse("03/02/2025"), "Título Teste 1", "Mensagem teste 1", new AuthorDTO(maria));

                    postRepository.saveAll(List.of(post1, post2));
                    logger.info("Posts inseridos no banco!");

                    joao.setPosts(List.of(post1));
                    maria.setPosts(List.of(post2));

                    userRepository.saveAll(List.of(joao, maria));
                    logger.info("Usuários atualizados com seus posts!");
                } catch (ParseException e) {
                    logger.error("Erro ao converter data", e);
                }
            }
        };
    }
}