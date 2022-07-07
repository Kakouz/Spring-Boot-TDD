package br.com.tdd.api.services.impl;

import br.com.tdd.api.domain.User;
import br.com.tdd.api.repositories.UserRepository;
import br.com.tdd.api.services.UserService;
import br.com.tdd.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("User not found!"));
    }
}
