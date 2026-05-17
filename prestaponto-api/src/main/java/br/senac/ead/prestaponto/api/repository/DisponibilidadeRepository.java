package br.senac.ead.prestaponto.api.repository;

import br.senac.ead.prestaponto.api.entity.Disponibilidade;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, UUID> {

    List<Disponibilidade> findByPrestadorId(UUID prestadorId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM Disponibilidade d WHERE d.id = :id")
    Optional<Disponibilidade> findByIdForUpdate(@Param("id") UUID id);

    @Query("""
                SELECT COUNT(d) > 0
                FROM Disponibilidade d
                WHERE d.prestador.id = :prestadorId
                  AND d.diaSemana    = :diaSemana
                  AND d.horaInicio   < :horaFim
                  AND d.horaFim      > :horaInicio
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
