package br.senac.ead.prestaponto.api.controller;

import br.senac.ead.prestaponto.api.dto.request.DisponibilidadeRequestDTO;
import br.senac.ead.prestaponto.api.dto.response.DisponibilidadeResponseDTO;
import br.senac.ead.prestaponto.api.entity.Disponibilidade;
import br.senac.ead.prestaponto.api.entity.User;
import br.senac.ead.prestaponto.api.service.DisponibilidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/prestadores/disponibilidades")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class DisponibilidadePrestadorController {

    private final DisponibilidadeService disponibilidadeService;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(
        summary = "Cadastrar disponibilidade", 
        description = "Cadastra uma disponibilidade para o prestador autenticado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Disponibilidade cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<?> cadastrar(
            @AuthenticationPrincipal User prestador,
            @Valid @RequestBody DisponibilidadeRequestDTO request) {

        Disponibilidade disponibilidade = disponibilidadeService.cadastrar(prestador, modelMapper.map(request, Disponibilidade.class));
        
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest() 
                .path("/{id}")
                .buildAndExpand(disponibilidade.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(
        summary = "Atualizar disponibilidade", 
        description = "Atualiza uma disponibilidade pelo id."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Disponibilidade atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody DisponibilidadeRequestDTO request,
            @AuthenticationPrincipal User prestador
    ) {

        disponibilidadeService.atualizar(
            id, 
            modelMapper.map(request, Disponibilidade.class), 
            prestador
        );

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(
        summary = "Remover disponibilidade", 
        description = "Remove uma disponibilidade pelo id."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Disponibilidade removida com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(
            @PathVariable UUID id,
            @AuthenticationPrincipal User prestador
    ) {

        disponibilidadeService.remover(id, prestador);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('PROVIDER', 'CLIENT')")
    @Operation(
        summary = "Listar disponibilidades de um prestador", 
        description = "Lista as disponibilidades pelo id do prestador."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @GetMapping
    public ResponseEntity<Page<DisponibilidadeResponseDTO>> listar(
        @RequestParam UUID prestadorId,
        Pageable pageable        
    ) {
        
        Page<DisponibilidadeResponseDTO> disponibilidades = disponibilidadeService.listarPorPrestador(prestadorId, pageable)
            .map(d -> {
                var result = modelMapper.map(d, DisponibilidadeResponseDTO.class);
                result.setReservada(d.isReservada());
                return result;
            });
    
        return ResponseEntity.ok(disponibilidades);
    }



}
