package br.senac.ead.prestaponto.api.controller;

import br.senac.ead.prestaponto.api.dto.request.DisponibilidadeRequestDTO;
import br.senac.ead.prestaponto.api.dto.response.DisponibilidadeResponseDTO;
import br.senac.ead.prestaponto.api.service.DisponibilidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/disponibilidades")
@RequiredArgsConstructor
public class DisponibilidadeController {

    private final DisponibilidadeService disponibilidadeService;

    @PostMapping
    public ResponseEntity<DisponibilidadeResponseDTO> criar(
            @RequestParam UUID prestadorId,
            @Valid @RequestBody DisponibilidadeRequestDTO dto) {

        DisponibilidadeResponseDTO response = disponibilidadeService.criar(prestadorId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DisponibilidadeResponseDTO>> listar(
            @RequestParam UUID prestadorId) {

        List<DisponibilidadeResponseDTO> response = disponibilidadeService.listarPorPrestador(prestadorId);
        return ResponseEntity.ok(response);
    }
}
