package br.senac.ead.prestaponto.api.controller;

import br.senac.ead.prestaponto.api.dto.request.CatalogItemDTO;
import br.senac.ead.prestaponto.api.entity.CatalogItem;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import br.senac.ead.prestaponto.api.dto.response.DisponibilidadeResponseDTO;
import br.senac.ead.prestaponto.api.entity.User;
import br.senac.ead.prestaponto.api.service.DisponibilidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RestController
@RequestMapping("/disponibilidades")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class DisponibilidadeController {

    private final DisponibilidadeService service;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('PROVIDER', 'CLIENT')")
    @Operation(
        summary = "Buscar disponibilidade pelo id", 
        description = "Busca uma disponibilidade pelo seu id."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadeResponseDTO> buscarPorId(@PathVariable UUID id) {
        
        DisponibilidadeResponseDTO disponibilidade = modelMapper.map(service.buscarPorId(id), DisponibilidadeResponseDTO.class);
        return ResponseEntity.ok(disponibilidade);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @Operation(
        summary = "Buscar reservas do cliente", 
        description = "Busca as reservas do cliente autenticado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
    })
    @GetMapping("/reservas")
    public ResponseEntity<Page<DisponibilidadeResponseDTO>> buscarReservas(
        @AuthenticationPrincipal User cliente,
        Pageable pageable
    ) {
        
        Page<DisponibilidadeResponseDTO> disponibilidades = service.listarReservas(cliente, pageable)
            .map(d -> modelMapper.map(d, DisponibilidadeResponseDTO.class));
        return ResponseEntity.ok(disponibilidades);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @Operation(
        summary = "Reservar horário de um prestador", 
        description = "Reserva um horário específico de um prestador através do id da disponibilidade."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Horário reservado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
    })
    @PostMapping("/{id}")
    public ResponseEntity<?> reservar(
            @PathVariable UUID id,
            @AuthenticationPrincipal User cliente,
            CatalogItemDTO catalogItemDTO) {

        CatalogItem catalogItem = modelMapper.map(catalogItemDTO, CatalogItem.class);

        service.reservar(id, cliente, catalogItem.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('CLIENT')")
    @Operation(
        summary = "Cancelar reserva", 
        description = "Cancela um horário reservado pelo cliente."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reserva cancelada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<DisponibilidadeResponseDTO> cancelarReserva(
            @PathVariable UUID id,
            @AuthenticationPrincipal User cliente) {

        service.cancelarReserva(id, cliente);
        return ResponseEntity.noContent().build();
    }
}
