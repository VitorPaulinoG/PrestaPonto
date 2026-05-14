package br.senac.ead.prestaponto.api.service;

import br.senac.ead.prestaponto.api.entity.User;

import br.senac.ead.prestaponto.api.dto.request.DisponibilidadeRequestDTO;
import br.senac.ead.prestaponto.api.dto.response.DisponibilidadeResponseDTO;
import br.senac.ead.prestaponto.api.entity.Disponibilidade;
import br.senac.ead.prestaponto.api.repository.DisponibilidadeRepository;
import br.senac.ead.prestaponto.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisponibilidadeService {

    private final DisponibilidadeRepository disponibilidadeRepository;
    private final UserRepository userRepository;

    @Transactional
    public DisponibilidadeResponseDTO criar(UUID prestadorId, DisponibilidadeRequestDTO dto) {

        User prestador = userRepository.findById(prestadorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Prestador não encontrado: id=" + prestadorId));

        if (!dto.getHoraInicio().isBefore(dto.getHoraFim())) {
            throw new IllegalArgumentException(
                    "A hora de início deve ser anterior à hora de fim.");
        }

        boolean conflito = disponibilidadeRepository.existeConflito(
                prestadorId,
                dto.getDiaSemana(),
                dto.getHoraInicio(),
                dto.getHoraFim()
        );

        if (conflito) {
            throw new IllegalStateException(
                    "Já existe uma disponibilidade cadastrada que conflita com o horário informado.");
        }

        Disponibilidade disponibilidade = Disponibilidade.builder()
                .user(prestador)
                .diaSemana(dto.getDiaSemana())
                .horaInicio(dto.getHoraInicio())
                .horaFim(dto.getHoraFim())
                .build(); // status recebe pendente por padrão

        disponibilidade = disponibilidadeRepository.save(disponibilidade);

        return toResponse(disponibilidade);
    }

    @Transactional(readOnly = true)
    public List<DisponibilidadeResponseDTO> listarPorPrestador(UUID prestadorId) {

        if (!userRepository.existsById(prestadorId)) {
            throw new EntityNotFoundException(
                    "Prestador não encontrado: id=" + prestadorId);
        }

        return disponibilidadeRepository.findByPrestadorId(prestadorId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private DisponibilidadeResponseDTO toResponse(Disponibilidade d) {
        return DisponibilidadeResponseDTO.builder()
                .id(d.getId())
                .prestadorId(d.getUser().getId())
                .diaSemana(d.getDiaSemana())
                .horaInicio(d.getHoraInicio())
                .horaFim(d.getHoraFim())
                .status(d.getStatus())
                .build();
    }
}
