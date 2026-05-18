package br.senac.ead.prestaponto.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadeResponseDTO {

    private UUID id;
    private UUID prestadorId;
    private LocalDate diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private boolean reservada;
}
