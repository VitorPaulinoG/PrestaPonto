package br.senac.ead.prestaponto.api.utils;

import br.senac.ead.prestaponto.api.entity.User;
import br.senac.ead.prestaponto.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserResolverHelper {

    private final UserRepository userRepository;

    public UUID resolveId(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .map(User::getId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário autenticado não encontrado: " + userDetails.getUsername()));
    }
}
