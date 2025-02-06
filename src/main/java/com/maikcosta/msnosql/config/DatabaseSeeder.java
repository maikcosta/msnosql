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
            // Formatar data
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            // Verifica e insere usuários
            if (userRepository.count() == 0) {
                List<User> users = List.of(
                        new User(null, "João Silva", "joao@email.com"),
                        new User(null, "Maria Souza", "maria@email.com")
                );
                userRepository.saveAll(users);
                logger.info("Usuários inseridos no banco!");
            }

            // Buscar os usuários atualizados
            Optional<User> joao = userRepository.findByEmail("joao@email.com");
            Optional<User> maria = userRepository.findByEmail("maria@email.com");

            if (joao.isEmpty() || maria.isEmpty()) {
                logger.warn("Usuários não encontrados! Pulando inserção de posts.");
                return;
            }

            // Verifica e insere posts
            if (postRepository.count() == 0) {
                try {
                    List<Post> posts = List.of(
                            new Post(null, simpleDateFormat.parse("02/02/2025"), "Título Teste", "Mensagem teste", new AuthorDTO(joao.get())),
                            new Post(null, simpleDateFormat.parse("03/02/2025"), "Título Teste 1", "Mensagem teste 1", new AuthorDTO(maria.get()))
                    );
                    postRepository.saveAll(posts);
                    logger.info("Posts inseridos no banco!");
                } catch (ParseException e) {
                    logger.error("Erro ao converter data", e);
                }
            }
        };
    }
}