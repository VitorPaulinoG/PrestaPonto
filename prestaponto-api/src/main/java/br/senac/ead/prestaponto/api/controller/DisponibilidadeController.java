package br.senac.ead.prestaponto.api.controller;

import br.senac.ead.prestaponto.api.dto.request.DisponibilidadeRequestDTO;
import br.senac.ead.prestaponto.api.dto.response.DisponibilidadeResponseDTO;
import br.senac.ead.prestaponto.api.service.DisponibilidadeService;
import br.senac.ead.prestaponto.api.utils.UserResolverHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disponibilidades")
@RequiredArgsConstructor
public class DisponibilidadeController {

    private final DisponibilidadeService disponibilidadeService;
    private final UserResolverHelper userResolver; // helper que extrai o ID do JWT

    @PostMapping
    @PreAuthorize("hasRole('PRESTADOR')")
    public ResponseEntity<DisponibilidadeResponseDTO> cadastrar(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody DisponibilidadeRequestDTO request) {

        UUID prestadorId = userResolver.resolveId(userDetails);
        DisponibilidadeResponseDTO response = disponibilidadeService.cadastrar(prestadorId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PRESTADOR')")
    public ResponseEntity<DisponibilidadeResponseDTO> atualizar(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody DisponibilidadeRequestDTO request) {

        UUID prestadorId = userResolver.resolveId(userDetails);
        DisponibilidadeResponseDTO response = disponibilidadeService.atualizar(id, prestadorId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PRESTADOR', 'CLIENTE')")
    public ResponseEntity<List<DisponibilidadeResponseDTO>> listar(
            @RequestParam UUID prestadorId) {

        return ResponseEntity.ok(disponibilidadeService.listarPorPrestador(prestadorId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRESTADOR', 'CLIENTE')")
    public ResponseEntity<DisponibilidadeResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(disponibilidadeService.buscarPorId(id));
    }

    @PostMapping("/{id}/reservar")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<DisponibilidadeResponseDTO> reservar(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {

        UUID clienteId = userResolver.resolveId(userDetails);
        return ResponseEntity.ok(disponibilidadeService.reservar(id, clienteId));
    }

    @DeleteMapping("/{id}/reservar")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<DisponibilidadeResponseDTO> cancelarReserva(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {

        UUID clienteId = userResolver.resolveId(userDetails);
        return ResponseEntity.ok(disponibilidadeService.cancelarReserva(id, clienteId));
    }
}
