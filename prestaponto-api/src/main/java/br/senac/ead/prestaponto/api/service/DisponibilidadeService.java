package br.senac.ead.prestaponto.api.service;

import br.senac.ead.prestaponto.api.entity.CatalogItem;
import br.senac.ead.prestaponto.api.entity.Disponibilidade;
import br.senac.ead.prestaponto.api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DisponibilidadeService {

    Disponibilidade cadastrar(User prestador, Disponibilidade disponibilidade);
    
    Disponibilidade atualizar(UUID id, Disponibilidade disponibilidade, User prestador);
    
    void remover(UUID id, User prestador);
    
    Disponibilidade reservar(UUID id, User cliente, UUID catalogItemID);

    Disponibilidade cancelarReserva(UUID id, User cliente);
    
    Page<Disponibilidade> listarReservas(User cliente, Pageable pageable);
    
    Page<Disponibilidade> listarPorPrestador(UUID prestadorId, Pageable pageable);

    Page<Disponibilidade> listarReservadasPorPrestador(User prestador, Pageable pageable);

    Disponibilidade buscarPorId(UUID id);

}
