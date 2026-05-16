package br.senac.ead.prestaponto.api.service;

import br.senac.ead.prestaponto.api.dto.request.DisponibilidadeRequestDTO;
import br.senac.ead.prestaponto.api.dto.response.DisponibilidadeResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DisponibilidadeService {

    DisponibilidadeResponseDTO cadastrar(UUID prestadorId, DisponibilidadeRequestDTO request);

    DisponibilidadeResponseDTO atualizar(UUID disponibilidadeId, UUID prestadorId,
                                          DisponibilidadeRequestDTO request);

    List<DisponibilidadeResponseDTO> listarPorPrestador(UUID prestadorId);

    DisponibilidadeResponseDTO buscarPorId(UUID disponibilidadeId);

    DisponibilidadeResponseDTO reservar(UUID disponibilidadeId, UUID clienteId);

    DisponibilidadeResponseDTO cancelarReserva(UUID disponibilidadeId, UUID clienteId);
}
