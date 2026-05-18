package br.senac.ead.prestaponto.api.repository;

import br.senac.ead.prestaponto.api.entity.Disponibilidade;
import br.senac.ead.prestaponto.api.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, UUID> {

    Page<Disponibilidade> findByPrestadorId(UUID prestadorId, Pageable pageable);

    Page<Disponibilidade> findAllByCliente(User cliente, Pageable pageable);

    @Query("""
        SELECT COUNT(d) > 0
        FROM Disponibilidade d
        WHERE d.prestador.id    = :prestadorId
            AND d.diaSemana     = :diaSemana
            AND d.horaInicio    < :horaFim
            AND d.horaFim       > :horaInicio
            AND (:excluirId IS NULL OR d.id <> :excluirId)
    """)
    boolean existeConflitoPrestador(
            @Param("prestadorId") UUID prestadorId,
            @Param("diaSemana") LocalDate diaSemana,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFim") LocalTime horaFim,
            @Param("excluirId") UUID excluirId

    );

    @Query("""
        SELECT COUNT(d) > 0
        FROM Disponibilidade d
        WHERE d.cliente.id = :clienteId
            AND d.diaSemana  = :diaSemana
            AND d.horaInicio < :horaFim
            AND d.horaFim    > :horaInicio
    """)
    boolean existeConflitoCliente(
            @Param("clienteId") UUID clienteId,
            @Param("diaSemana") LocalDate diaSemana,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFim") LocalTime horaFim
    );

}
