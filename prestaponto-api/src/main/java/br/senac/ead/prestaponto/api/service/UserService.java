package br.senac.ead.prestaponto.api.service;

import java.util.Optional;

import br.senac.ead.prestaponto.api.entity.User;

public interface UserService {
    User register(User user);
    Optional<User> findByEmail(String email);
}
