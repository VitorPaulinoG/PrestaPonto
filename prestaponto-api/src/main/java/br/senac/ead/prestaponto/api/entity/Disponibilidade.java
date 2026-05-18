package br.senac.ead.prestaponto.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tb_disponibilidade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disponibilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestador_id", nullable = false)
    private User prestador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private User cliente;

    @Column(name = "dia_semana", nullable = false)
    private LocalDate diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    @ManyToOne
    @JoinColumn(name = "catalog_item_id")
    private CatalogItem catalogItem;

    public boolean isReservada() {
        return this.cliente != null;
    }

    public void validarIntervalo() {
        if (!getHoraInicio().isBefore(getHoraFim())) {
            throw new IllegalArgumentException("A hora de início deve ser anterior à hora de fim.");
        }
    }
}