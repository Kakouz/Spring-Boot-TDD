package br.com.tdd.api.services.impl;

import br.com.tdd.api.domain.User;
import br.com.tdd.api.domain.dto.UserDTO;
import br.com.tdd.api.repositories.UserRepository;
import br.com.tdd.api.services.UserService;
import br.com.tdd.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("User not found!"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(UserDTO obj) {
        return userRepository.save(mapper.map(obj, User.class));
    }
}
