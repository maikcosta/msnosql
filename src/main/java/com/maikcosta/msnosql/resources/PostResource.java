package com.maikcosta.msnosql.resources;

import com.maikcosta.msnosql.domain.Post;
import com.maikcosta.msnosql.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/posts")

public class PostResource {

    @Autowired
    private PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @GetMapping
    public ResponseEntity<List<Post>> findAll() {
        List<Post> posts = postService.findAll();
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        logger.debug("Posts encontrados: " + posts);
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id){
        Post post = postService.findById(id);
        return ResponseEntity.ok().body(post);
    }
}
