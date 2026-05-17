package br.senac.ead.prestaponto.api.service.impl;

import br.senac.ead.prestaponto.api.dto.request.DisponibilidadeRequestDTO;
import br.senac.ead.prestaponto.api.dto.response.DisponibilidadeResponseDTO;
import br.senac.ead.prestaponto.api.entity.Disponibilidade;
import br.senac.ead.prestaponto.api.entity.User;
import br.senac.ead.prestaponto.api.exception.ReservaConcorrenteException;
import br.senac.ead.prestaponto.api.repository.DisponibilidadeRepository;
import br.senac.ead.prestaponto.api.service.DisponibilidadeService;
import br.senac.ead.prestaponto.api.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DisponibilidadeServiceImpl implements DisponibilidadeService {

    private final DisponibilidadeRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public DisponibilidadeResponseDTO cadastrar(UUID prestadorId,
                                                DisponibilidadeRequestDTO request) {
        User prestador = buscarUser(prestadorId);

        Disponibilidade disponibilidade = Disponibilidade.builder()
                .prestador(prestador)
                .diaSemana(request.getDiaSemana())
                .horaInicio(request.getHoraInicio())
                .horaFim(request.getHoraFim())
                .build();

        disponibilidade.validarIntervalo();
        validarConflitoPrestador(prestadorId, disponibilidade);

        return toResponse(repository.save(disponibilidade));
    }

    @Override
    @Transactional
    public DisponibilidadeResponseDTO atualizar(UUID disponibilidadeId, UUID prestadorId,
                                                DisponibilidadeRequestDTO request) {
        Disponibilidade registrada = buscarEntidade(disponibilidadeId);
        verificarPropriedadePrestador(registrada, prestadorId);

        registrada.setDiaSemana(request.getDiaSemana());
        registrada.setHoraInicio(request.getHoraInicio());
        registrada.setHoraFim(request.getHoraFim());

        registrada.validarIntervalo();
        validarConflitoPrestador(prestadorId, registrada);

        return toResponse(repository.save(registrada));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DisponibilidadeResponseDTO> listarPorPrestador(UUID prestadorId) {
        if (userService.findById(prestadorId).isEmpty()) {
            throw new EntityNotFoundException("Prestador não encontrado: id=" + prestadorId);
        }
        return repository.findByPrestadorId(prestadorId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DisponibilidadeResponseDTO buscarPorId(UUID disponibilidadeId) {
        return toResponse(buscarEntidade(disponibilidadeId));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public DisponibilidadeResponseDTO reservar(UUID disponibilidadeId, UUID clienteId) {
        try {
            Disponibilidade disponibilidade = repository.findByIdForUpdate(disponibilidadeId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Disponibilidade não encontrada: id=" + disponibilidadeId));

            if (disponibilidade.isReservada()) {
                throw new ReservaConcorrenteException();
            }

            validarConflitoCliente(clienteId, disponibilidade);

            disponibilidade.setCliente(buscarUser(clienteId));

            return toResponse(repository.save(disponibilidade));

        } catch (OptimisticLockException | CannotAcquireLockException ex) {
            log.warn("Conflito de concorrência ao reservar disponibilidade {}: {}",
                    disponibilidadeId, ex.getMessage());
            throw new ReservaConcorrenteException();
        }
    }

    @Override
    @Transactional
    public DisponibilidadeResponseDTO cancelarReserva(UUID disponibilidadeId, UUID clienteId) {
        Disponibilidade disponibilidade = buscarEntidade(disponibilidadeId);

        if (!disponibilidade.isReservada()) {
            throw new IllegalStateException("Este horário não possui reserva.");
        }

        verificarPropriedadeCliente(disponibilidade, clienteId);
        disponibilidade.setCliente(null);

        return toResponse(repository.save(disponibilidade));
    }

    private User buscarUser(UUID id) {
        return userService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário não encontrado: id=" + id));
    }

    private Disponibilidade buscarEntidade(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Disponibilidade não encontrada: id=" + id));
    }

    private void validarConflitoPrestador(UUID prestadorId, Disponibilidade disponibilidade) {
        boolean conflito = repository.existeConflitoPrestador(
                prestadorId,
                disponibilidade.getDiaSemana(),
                disponibilidade.getHoraInicio(),
                disponibilidade.getHoraFim(),
                disponibilidade.getId()
        );
        if (conflito) {
            throw new IllegalStateException(
                    "Já existe uma disponibilidade que conflita com o intervalo informado.");
        }
    }

    private void validarConflitoCliente(UUID clienteId, Disponibilidade disponibilidade) {
        boolean conflito = repository.existeConflitoCliente(
                clienteId,
                disponibilidade.getDiaSemana(),
                disponibilidade.getHoraInicio(),
                disponibilidade.getHoraFim()
        );
        if (conflito) {
            throw new IllegalStateException(
                    "O usuário já possui uma reserva que conflita com este horário.");
        }
    }

    private void verificarPropriedadePrestador(Disponibilidade disponibilidade, UUID prestadorId) {
        if (!disponibilidade.getPrestador().getId().equals(prestadorId)) {
            throw new AccessDeniedException(
                    "O usuário não tem permissão para modificar esta disponibilidade.");
        }
    }

    private void verificarPropriedadeCliente(Disponibilidade d, UUID clienteId) {
        if (!d.getCliente().getId().equals(clienteId)) {
            throw new AccessDeniedException(
                    "Você não tem permissão para cancelar esta reserva.");
        }
    }

    private DisponibilidadeResponseDTO toResponse(Disponibilidade d) {
        return DisponibilidadeResponseDTO.builder()
                .id(d.getId())
                .prestadorId(d.getPrestador().getId())
                .clienteId(d.getCliente() != null ? d.getCliente().getId() : null)
                .diaSemana(d.getDiaSemana())
                .horaInicio(d.getHoraInicio())
                .horaFim(d.getHoraFim())
                .build();
    }
}
