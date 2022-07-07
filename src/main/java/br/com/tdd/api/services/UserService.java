package br.com.tdd.api.services;


import br.com.tdd.api.domain.User;

public interface UserService {

    User findById (Integer id);
}
