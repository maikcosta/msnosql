package com.maikcosta.msnosql.services;

import com.maikcosta.msnosql.domain.User;
import com.maikcosta.msnosql.dto.UserDTO;
import com.maikcosta.msnosql.repository.UserRepository;
import com.maikcosta.msnosql.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    public User insert(User user){
        return userRepository.insert(user);
    }

    public void delete(String id){
        findById(id);
        userRepository.deleteById(id);
    }

    public User update(User user) {
        User updateUser = findById(user.getId());
        updateData(updateUser, user);
        return userRepository.save(updateUser);
    }

    private void updateData(User updateUser, User obj) {
        updateUser.setName(obj.getName());
        updateUser.setEmail(obj.getEmail());
    }


    public User fromDTO(UserDTO userDTO){
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail());
    }
}
