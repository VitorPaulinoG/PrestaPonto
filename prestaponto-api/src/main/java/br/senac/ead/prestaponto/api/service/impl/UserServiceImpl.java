package br.senac.ead.prestaponto.api.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.senac.ead.prestaponto.api.entity.User;
import br.senac.ead.prestaponto.api.exception.EntityAlreadyExistsException;
import br.senac.ead.prestaponto.api.repository.UserRepository;
import br.senac.ead.prestaponto.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.encoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException("O usuário já existe.");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }


}
