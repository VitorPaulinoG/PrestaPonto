package br.senac.ead.prestaponto.api.service.impl;

import br.senac.ead.prestaponto.api.dto.request.DisponibilidadeRequestDTO;
import br.senac.ead.prestaponto.api.dto.response.DisponibilidadeResponseDTO;
import br.senac.ead.prestaponto.api.entity.Disponibilidade;
import br.senac.ead.prestaponto.api.entity.User;
import br.senac.ead.prestaponto.api.repository.DisponibilidadeRepository;
import br.senac.ead.prestaponto.api.repository.UserRepository;
import br.senac.ead.prestaponto.api.service.DisponibilidadeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisponibilidadeServiceImpl implements DisponibilidadeService {

    private final DisponibilidadeRepository disponibilidadeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public DisponibilidadeResponseDTO cadastrar(UUID prestadorId, DisponibilidadeRequestDTO request) {

        User prestador = buscarUser(prestadorId);

        validarIntervalo(request);
        validarConflitoPrestador(prestadorId, request, null);

        Disponibilidade disponibilidade = Disponibilidade.builder()
                .prestador(prestador)
                .diaSemana(request.getDiaSemana())
                .horaInicio(request.getHoraInicio())
                .horaFim(request.getHoraFim())
                .build();

        return toResponse(disponibilidadeRepository.save(disponibilidade));
    }

    @Override
    @Transactional
    public DisponibilidadeResponseDTO atualizar(UUID disponibilidadeId, UUID prestadorId,
                                                 DisponibilidadeRequestDTO request) {

        Disponibilidade disponibilidade = buscarDisponibilidade(disponibilidadeId);

        verificarPropriedadePrestador(disponibilidade, prestadorId);
        validarIntervalo(request);
        validarConflitoPrestador(prestadorId, request, disponibilidadeId);

        disponibilidade.setDiaSemana(request.getDiaSemana());
        disponibilidade.setHoraInicio(request.getHoraInicio());
        disponibilidade.setHoraFim(request.getHoraFim());

        return toResponse(disponibilidadeRepository.save(disponibilidade));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DisponibilidadeResponseDTO> listarPorPrestador(UUID prestadorId) {
        if (!userRepository.existsById(prestadorId)) {
            throw new EntityNotFoundException("Prestador não encontrado: id=" + prestadorId);
        }
        return disponibilidadeRepository.findByPrestadorId(prestadorId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DisponibilidadeResponseDTO buscarPorId(UUID disponibilidadeId) {
        return toResponse(buscarDisponibilidade(disponibilidadeId));
    }

    @Override
    @Transactional
    public DisponibilidadeResponseDTO reservar(UUID disponibilidadeId, UUID clienteId) {

        Disponibilidade disponibilidade = buscarDisponibilidade(disponibilidadeId);

        if (disponibilidade.isReservada()) {
            throw new IllegalStateException("Este horário já está reservado.");
        }

        boolean conflito = disponibilidadeRepository.existeConflitoCliente(
                clienteId,
                disponibilidade.getDiaSemana(),
                disponibilidade.getHoraInicio(),
                disponibilidade.getHoraFim()
        );

        if (conflito) {
            throw new IllegalStateException(
                    "Você já possui uma reserva que conflita com este horário.");
        }

        User cliente = buscarUser(clienteId);
        disponibilidade.setCliente(cliente);

        return toResponse(disponibilidadeRepository.save(disponibilidade));
    }

    @Override
    @Transactional
    public DisponibilidadeResponseDTO cancelarReserva(UUID disponibilidadeId, UUID clienteId) {

        Disponibilidade disponibilidade = buscarDisponibilidade(disponibilidadeId);

        if (!disponibilidade.isReservada()) {
            throw new IllegalStateException("Este horário não possui reserva.");
        }

        verificarPropriedadeCliente(disponibilidade, clienteId);

        disponibilidade.setCliente(null);

        return toResponse(disponibilidadeRepository.save(disponibilidade));
    }

    private User buscarUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: id=" + id));
    }

    private Disponibilidade buscarDisponibilidade(UUID id) {
        return disponibilidadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Disponibilidade não encontrada: id=" + id));
    }

    private void validarIntervalo(DisponibilidadeRequestDTO request) {
        if (!request.getHoraInicio().isBefore(request.getHoraFim())) {
            throw new IllegalArgumentException(
                    "A hora de início deve ser anterior à hora de fim.");
        }
    }

    private void validarConflitoPrestador(UUID prestadorId,
                                          DisponibilidadeRequestDTO request,
                                          UUID excluirId) {
        boolean conflito = disponibilidadeRepository.existeConflitoPrestador(
                prestadorId,
                request.getDiaSemana(),
                request.getHoraInicio(),
                request.getHoraFim(),
                excluirId
        );
        if (conflito) {
            throw new IllegalStateException(
                    "Já existe uma disponibilidade que conflita com o intervalo informado.");
        }
    }

    private void verificarPropriedadePrestador(Disponibilidade d, UUID prestadorId) {
        if (!d.getPrestador().getId().equals(prestadorId)) {
            throw new AccessDeniedException(
                    "Você não tem permissão para modificar esta disponibilidade.");
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
