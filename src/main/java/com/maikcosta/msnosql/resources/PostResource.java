package com.maikcosta.msnosql.resources;

import com.maikcosta.msnosql.domain.Post;
import com.maikcosta.msnosql.resources.util.URL;
import com.maikcosta.msnosql.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/posts")

public class PostResource {

    @Autowired
    private PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    @Operation(summary = "Retorna todos os posts",description = "Metodo que retorna todos os posts",tags = "Posts")
    @GetMapping
    public ResponseEntity<List<Post>> findAll() {
        List<Post> posts = postService.findAll();
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        logger.debug("Posts encontrados: " + posts);
        return ResponseEntity.ok().body(posts);
    }
    @Operation(summary = "Busca post por id",description = "Metodo que retorna o post por ID",tags = "Posts")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id){
        Post post = postService.findById(id);
        return ResponseEntity.ok().body(post);
    }
    @Operation(summary = "Busca por título do post",description = "Metodo que retorna todos os posts que contém o parametro",tags = "Posts")
    @GetMapping(path = "/titlesearch")
    public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "")String text){
        text = URL.decodeParam(text);
        List<Post> posts = postService.findByTitle(text);
        return ResponseEntity.ok().body(posts);
    }

}
