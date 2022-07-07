package br.com.tdd.api.services;


import br.com.tdd.api.domain.User;

import java.util.List;

public interface UserService {

    User findById (Integer id);
    List<User> findAll();
}
