package br.senac.ead.prestaponto.api.service;

import java.util.Optional;
import java.util.UUID;

import br.senac.ead.prestaponto.api.entity.User;

public interface UserService {
    User register(User user);
    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);

    boolean existsById(UUID id);
}