package br.senac.ead.prestaponto.api.dto.response;

import br.senac.ead.prestaponto.api.entity.Disponibilidade;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Builder
public class DisponibilidadeResponseDTO {

    private Long id;
    private UUID prestadorId;
    private LocalDate diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Disponibilidade.StatusDisponibilidade status;
}
