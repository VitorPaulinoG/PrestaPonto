package br.senac.ead.prestaponto.api.service;

import br.senac.ead.prestaponto.api.entity.Disponibilidade;
import br.senac.ead.prestaponto.api.entity.User;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DisponibilidadeService {

    /* PRESTADOR */
    Disponibilidade cadastrar(User prestador, Disponibilidade disponibilidade);
    
    Disponibilidade atualizar(UUID id, Disponibilidade disponibilidade, User prestador);
    
    void remover(UUID id, User prestador);
    
    
    /* CLIENTE */
    Disponibilidade reservar(UUID id, User cliente);
    
    Disponibilidade cancelarReserva(UUID id, User cliente);
    
    Page<Disponibilidade> listarReservas(User cliente, Pageable pageable);
    
    /* AMBOS */
    Page<Disponibilidade> listarPorPrestador(UUID prestadorId, Pageable pageable);
    
    Disponibilidade buscarPorId(UUID id);
}
