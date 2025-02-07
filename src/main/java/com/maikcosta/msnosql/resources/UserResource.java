package com.maikcosta.msnosql.resources;

import com.maikcosta.msnosql.domain.Post;
import com.maikcosta.msnosql.domain.User;
import com.maikcosta.msnosql.dto.UserDTO;
import com.maikcosta.msnosql.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/users")

public class UserResource {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Operation(summary = "Retorna todos os usuários",description = "Metodo que retorna todos os usuários",tags = "User")
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        List<User> users = userService.findAll();
        List<UserDTO> usersDTO = users.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        logger.debug("Usuários encontrados: " + users);
        return ResponseEntity.ok().body(usersDTO);
    }
    @Operation(summary = "Busca usuário por ID",description = "Metodo que retorna usuário por ID",tags = "User")
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(new UserDTO(user));
    }
    @Operation(summary = "Inserir usuário",description = "Metodo para inserir usuários",tags = "User")
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UserDTO userDTO){
        User user = userService.fromDTO(userDTO);
        user = userService.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @Operation(summary = "Deleta usuário",description = "Metodo para deletar usuários",tags = "User")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable String id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Atualizar usuário",description = "Metodo para atualizar usuários",tags = "User")
    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> update(@RequestBody UserDTO userDTO, @PathVariable String id){
        User user = userService.fromDTO(userDTO);
        user.setId(id);
        user = userService.update(user);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Buscar post por usuário",description = "Metodo busca posts por usuários",tags = "User")
    @GetMapping(path = "/{id}/posts")
    public ResponseEntity<List<Post>> findPosts(@PathVariable String id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user.getPosts());
    }
}
