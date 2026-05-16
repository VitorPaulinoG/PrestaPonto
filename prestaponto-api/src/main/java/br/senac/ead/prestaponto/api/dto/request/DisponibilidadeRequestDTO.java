package br.senac.ead.prestaponto.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
public class DisponibilidadeRequestDTO {

    @Schema(description = "Dia da Semana", example = "2026-05-16")
    @NotNull(message = "O dia da semana é obrigatório")
    private LocalDate diaSemana;
    
    @Schema(description = "Horário de início", example = "08:30", type = "string", pattern = "HH:mm")
    @NotNull(message = "A hora de início é obrigatória")
    private LocalTime horaInicio;

    @Schema(description = "Horário de término", example = "17:45", type = "string", pattern = "HH:mm")
    @NotNull(message = "A hora de fim é obrigatória")
    private LocalTime horaFim;
}
