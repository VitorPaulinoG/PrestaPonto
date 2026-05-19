package br.senac.ead.prestaponto.api.service.impl;

import br.senac.ead.prestaponto.api.entity.CatalogItem;
import br.senac.ead.prestaponto.api.entity.Disponibilidade;
import br.senac.ead.prestaponto.api.entity.User;
import br.senac.ead.prestaponto.api.entity.UserType;
import br.senac.ead.prestaponto.api.exception.ReservaConcorrenteException;
import br.senac.ead.prestaponto.api.repository.DisponibilidadeRepository;
import br.senac.ead.prestaponto.api.repository.UserRepository;
import br.senac.ead.prestaponto.api.service.DisponibilidadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class DisponibilidadeConcorrenciaTest {

    @Autowired
    private DisponibilidadeService service;
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;
    @Autowired
    private UserRepository userRepository;

    private User prestador;
    private List<User> clientes;
    private Disponibilidade disponibilidade;

    @BeforeEach
    @Transactional
    void setUp() {
        disponibilidadeRepository.deleteAll();
        userRepository.deleteAll();

        prestador = userRepository.save(User.builder()
                .name("Prestador Teste")
                .email("prestador@test.com")
                .password("senha123")
                .userType(UserType.PROVIDER)
                .build());

        clientes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            clientes.add(userRepository.save(User.builder()
                    .name("Cliente " + i)
                    .email("cliente" + i + "@test.com")
                    .password("senha123")
                    .userType(UserType.CLIENT)
                    .build()));
        }

        disponibilidade = disponibilidadeRepository.save(Disponibilidade.builder()
                .prestador(prestador)
                .diaSemana(LocalDate.now().plusDays(1))
                .horaInicio(LocalTime.of(9, 0))
                .horaFim(LocalTime.of(10, 0))
                .build());
    }

    @Test
    @DisplayName("Exatamente 1 reserva deve ser confirmada quando 10 clientes tentam simultaneamente")
    void devePermitirApenasUmaReservaSobConcorrencia() throws InterruptedException {
        int totalThreads = 10;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(totalThreads);
        AtomicInteger sucessos = new AtomicInteger(0);
        AtomicInteger conflitos = new AtomicInteger(0);
        List<Exception> errosInesperados = Collections.synchronizedList(new ArrayList<>());

        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        for (int i = 0; i < totalThreads; i++) {
            final User cliente = clientes.get(i);
            final var dispId = disponibilidade.getId();

            executor.submit(() -> {
                try {
                    startLatch.await();
                    service.reservar(dispId, cliente, new CatalogItem().getId());
                    sucessos.incrementAndGet();
                } catch (ReservaConcorrenteException | IllegalStateException ex) {
                    conflitos.incrementAndGet();
                } catch (Exception ex) {
                    errosInesperados.add(ex);
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        boolean concluido = finishLatch.await(30, TimeUnit.SECONDS);
        executor.shutdown();

        assertThat(concluido).as("Todas as threads devem concluir em 30s").isTrue();
        assertThat(errosInesperados)
                .as("Erros inesperados: " + errosInesperados).isEmpty();
        assertThat(sucessos.get())
                .as("Exatamente 1 reserva deve ser confirmada")
                .isEqualTo(1);
        assertThat(conflitos.get())
                .as("Os 9 restantes devem receber conflito")
                .isEqualTo(totalThreads - 1);

        Disponibilidade salva = disponibilidadeRepository
                .findById(disponibilidade.getId()).orElseThrow();
        assertThat(salva.isReservada()).isTrue();
    }

    @Test
    @DisplayName("A version deve ser incrementada após a reserva")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void versionDeveIncrementarAposReserva() {
        long versionInicial = disponibilidadeRepository
                .findById(disponibilidade.getId()).orElseThrow().getVersion();

        service.reservar(disponibilidade.getId(), clientes.getFirst(), new CatalogItem().getId());

        long versionFinal = disponibilidadeRepository
                .findById(disponibilidade.getId()).orElseThrow().getVersion();

        assertThat(versionFinal)
                .as("Version deve incrementar após reserva")
                .isEqualTo(versionInicial + 1);
    }

    @Test
    @DisplayName("Tentar reservar horário já ocupado deve lançar ReservaConcorrenteException")
    void deveRejeitarReservaDuplicada() {
        service.reservar(disponibilidade.getId(), clientes.get(0), new CatalogItem().getId());

        assertThatThrownBy(() ->
                service.reservar(disponibilidade.getId(), clientes.get(1), new CatalogItem().getId()))
                .isInstanceOf(ReservaConcorrenteException.class);
    }

    @Test
    @DisplayName("Cada disponibilidade deve ter exatamente 1 reserva quando disputadas em paralelo")
    void cadaDisponibilidadeDeveTerminarComUmaReserva() throws InterruptedException {
        List<Disponibilidade> disponibilidades = new ArrayList<>();
        for (int h = 10; h < 15; h++) {
            disponibilidades.add(disponibilidadeRepository.save(Disponibilidade.builder()
                    .prestador(prestador)
                    .diaSemana(LocalDate.now().plusDays(2))
                    .horaInicio(LocalTime.of(h, 0))
                    .horaFim(LocalTime.of(h + 1, 0))
                    .build()));
        }

        int totalTasks = disponibilidades.size() * 2;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(totalTasks);
        AtomicInteger sucessos = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(totalTasks);

        for (Disponibilidade disp : disponibilidades) {
            for (int i = 0; i < 2; i++) {
                final User cliente = clientes.get(i);
                final var dispId = disp.getId();
                executor.submit(() -> {
                    try {
                        startLatch.await();
                        service.reservar(dispId, cliente, new CatalogItem().getId());
                        sucessos.incrementAndGet();
                    } catch (ReservaConcorrenteException | IllegalStateException ignored) {
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        finishLatch.countDown();
                    }
                });
            }
        }

        startLatch.countDown();
        finishLatch.await(30, TimeUnit.SECONDS);
        executor.shutdown();

        assertThat(sucessos.get())
                .as("Cada disponibilidade deve ter exatamente 1 reserva confirmada")
                .isEqualTo(disponibilidades.size());
    }
}
