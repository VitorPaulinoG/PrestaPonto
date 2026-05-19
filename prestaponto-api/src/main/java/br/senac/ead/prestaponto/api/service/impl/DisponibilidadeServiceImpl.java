package br.senac.ead.prestaponto.api.service.impl;

import br.senac.ead.prestaponto.api.entity.CatalogItem;
import br.senac.ead.prestaponto.api.entity.Disponibilidade;
import br.senac.ead.prestaponto.api.entity.User;
import br.senac.ead.prestaponto.api.repository.DisponibilidadeRepository;
import br.senac.ead.prestaponto.api.service.DisponibilidadeService;
import br.senac.ead.prestaponto.api.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisponibilidadeServiceImpl implements DisponibilidadeService {

    private final DisponibilidadeRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public Disponibilidade cadastrar(User prestador, Disponibilidade disponibilidade) {

        disponibilidade.validarIntervalo();
        validarConflitoPrestador(prestador.getId(), disponibilidade);
        disponibilidade.setPrestador(buscarUser(prestador.getId()));
        disponibilidade.setCatalogItemID(null);

        return repository.save(disponibilidade);
    }

    @Override
    @Transactional
    public Disponibilidade atualizar(
            UUID id,
            Disponibilidade disponibilidade,
            User prestador
    ) {

        Disponibilidade disponbilidadeRegistrada = buscarPorId(id);
        verificarPropriedadePrestador(disponbilidadeRegistrada, prestador);

        disponibilidade.validarIntervalo();
        validarConflitoPrestador(prestador.getId(), disponbilidadeRegistrada);

        disponbilidadeRegistrada.setDiaSemana(disponibilidade.getDiaSemana());
        disponbilidadeRegistrada.setHoraInicio(disponibilidade.getHoraInicio());
        disponbilidadeRegistrada.setHoraFim(disponibilidade.getHoraFim());

        return repository.save(disponbilidadeRegistrada);
    }

    @Override
    @Transactional
    public void remover(
            UUID id,
            User prestador
    ) {
        Disponibilidade disponbilidadeRegistrada = buscarPorId(id);
        verificarPropriedadePrestador(disponbilidadeRegistrada, prestador);

        repository.delete(disponbilidadeRegistrada);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Disponibilidade> listarPorPrestador(UUID id, Pageable pageable) {
        if (!userService.existsById(id)) {
            throw new EntityNotFoundException("Prestador não encontrado: id=" + id);
        }

        return repository.findByPrestadorId(id, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Disponibilidade> listarReservadasPorPrestador(User prestador, Pageable pageable) {
        return repository.findByPrestadorIdAndClienteIsNotNull(prestador.getId(), pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public Disponibilidade buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Disponibilidade não encontrada: id=" + id));
    }

    @Override
    @Transactional
    public Disponibilidade reservar(UUID id, User cliente, UUID catalogItemID) {

        Disponibilidade disponibilidade = buscarPorId(id);

        if (disponibilidade.isReservada()) {
            throw new IllegalStateException("Este horário já está reservado.");
        }

        validarConflitoCliente(cliente.getId(), disponibilidade);

        disponibilidade.setCliente(buscarUser(cliente.getId()));

        disponibilidade.setCatalogItemID(catalogItemID);

        return repository.save(disponibilidade);
    }

    @Override
    @Transactional
    public Disponibilidade cancelarReserva(UUID id, User cliente) {

        Disponibilidade disponibilidade = buscarPorId(id);

        if (!disponibilidade.isReservada()) {
            throw new IllegalStateException("Este horário não possui reserva.");
        }

        verificarPropriedadeCliente(disponibilidade, cliente.getId());

        disponibilidade.setCliente(null);

        return repository.save(disponibilidade);
    }

    @Override
    @Transactional
    public Page<Disponibilidade> listarReservas(User cliente, Pageable pageable) {
        return repository.findAllByCliente(cliente, pageable);
    }

    private User buscarUser(UUID id) {
        return userService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: id=" + id));
    }

    private void validarConflitoPrestador(
            UUID prestadorId,
            Disponibilidade disponibilidade
    ) {
        boolean conflito = repository.existeConflitoPrestador(
                prestadorId,
                disponibilidade.getDiaSemana(),
                disponibilidade.getHoraInicio(),
                disponibilidade.getHoraFim(),
                disponibilidade.getId()
        );


        if (conflito) {
            throw new IllegalStateException(
                    "Já existe uma disponibilidade que conflita com o intervalo informado.");
        }
    }

    private void validarConflitoCliente(
            UUID clienteId,
            Disponibilidade disponibilidade
    ) {
        boolean conflito = repository.existeConflitoCliente(
                clienteId,
                disponibilidade.getDiaSemana(),
                disponibilidade.getHoraInicio(),
                disponibilidade.getHoraFim()
        );

        if (conflito) {
            throw new IllegalStateException(
                    "O usuário já possui uma reserva que conflita com este horário.");
        }

    }

    private void verificarPropriedadePrestador(Disponibilidade disponibilidade, User prestador) {
        if (!disponibilidade.getPrestador().getId().equals(prestador.getId())) {
            throw new AccessDeniedException(
                    "O usuário não tem permissão para modificar esta disponibilidade.");
        }
    }

    private void verificarPropriedadeCliente(Disponibilidade d, UUID clienteId) {
        if (!d.getCliente().getId().equals(clienteId)) {
            throw new AccessDeniedException(
                    "Você não tem permissão para cancelar esta reserva.");
        }
    }

}
