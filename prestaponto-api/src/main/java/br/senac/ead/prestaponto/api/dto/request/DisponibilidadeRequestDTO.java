package br.senac.ead.prestaponto.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class DisponibilidadeRequestDTO {

    @NotNull(message = "O dia da semana é obrigatório")
    private LocalDate diaSemana;

    @NotNull(message = "A hora de início é obrigatória")
    private LocalTime horaInicio;

    @NotNull(message = "A hora de fim é obrigatória")
    private LocalTime horaFim;
}
