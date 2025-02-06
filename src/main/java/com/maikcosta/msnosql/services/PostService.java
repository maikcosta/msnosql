package com.maikcosta.msnosql.services;

import com.maikcosta.msnosql.domain.Post;
import com.maikcosta.msnosql.domain.User;
import com.maikcosta.msnosql.dto.UserDTO;
import com.maikcosta.msnosql.repository.PostRepository;
import com.maikcosta.msnosql.repository.UserRepository;
import com.maikcosta.msnosql.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Post findById(String id){
        return postRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    public List<Post> findByTitle(String text){
        return postRepository.findByTitleContainingIgnoreCase(text);
    }
}
