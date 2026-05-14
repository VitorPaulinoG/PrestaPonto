package br.senac.ead.prestaponto.api.controller;

import java.net.URI;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.senac.ead.prestaponto.api.dto.CreateCatalogItemRequest;
import br.senac.ead.prestaponto.api.dto.GetCatalogItemResponse;
import br.senac.ead.prestaponto.api.entity.CatalogItem;
import br.senac.ead.prestaponto.api.entity.User;
import br.senac.ead.prestaponto.api.service.CatalogItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/catalog")
@SecurityRequirement(name = "bearerAuth")
public class CatalogItemController {
    private final CatalogItemService service;
    private final ModelMapper modelMapper;

    public CatalogItemController(CatalogItemService catalogItemService, ModelMapper modelMapper) {
        this.service = catalogItemService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(
        summary = "Registrar um novo serviço no catálogo do prestador", 
        description = "Registra um novo serviço no catálogo do prestador com as informações fornecidas."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Serviço registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
    })
    @PostMapping
    public ResponseEntity<?> register(
        @Valid @RequestBody CreateCatalogItemRequest request,
        @AuthenticationPrincipal User loggedUser
    ) {
        CatalogItem catalogItem = modelMapper.map(request, CatalogItem.class);
        catalogItem.setProvider(loggedUser);

        CatalogItem registeredItem = service.register(catalogItem);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest() 
            .path("/{id}")
            .buildAndExpand(registeredItem.getId())
            .toUri();
                
        return ResponseEntity.created(uri).build();
    }

    @Operation(
        summary = "Buscar serviços por filtros", 
        description = "Busca serviços de acordo com a categoria e prestador."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dados obtidos com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
    })
    @GetMapping
    public ResponseEntity<?> findByFilter(
        @RequestParam(required = false) UUID providerId, 
        @RequestParam(required = false) String category, 
        Pageable pageable
    ) {
        Page<GetCatalogItemResponse> response = service.findByFilter(providerId, category, pageable)
            .map(item -> modelMapper.map(item, GetCatalogItemResponse.class));
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Buscar serviço por id", 
        description = "Busca um serviço através do id passado por parâmetro."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dados obtidos com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
        @NotNull(message = "O parâmetro 'id' é obrigatório") 
        @PathVariable UUID id
    ) {
        GetCatalogItemResponse response = modelMapper.map(service.findById(id), GetCatalogItemResponse.class);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(
        summary = "Atualizar serviço por id", 
        description = "Atualiza um serviço."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Dados atualizados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @NotNull(message = "O parâmetro 'id' é obrigatório") 
        @PathVariable UUID id,
        @Valid @RequestBody CreateCatalogItemRequest request,
        @AuthenticationPrincipal User user
    ) {
        service.update(id, modelMapper.map(request, CatalogItem.class), user);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Deletar um serviço por id", 
        description = "Deleta um serviço."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Dados removidos com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
        @NotNull(message = "O parâmetro 'id' é obrigatório") 
        @PathVariable UUID id,
        @AuthenticationPrincipal User user
    ) {
        service.deleteById(id, user);
        return ResponseEntity.noContent().build();
    }
}
