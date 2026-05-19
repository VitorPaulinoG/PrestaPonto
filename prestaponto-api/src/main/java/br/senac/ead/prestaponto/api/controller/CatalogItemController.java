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

    public CatalogItemController(CatalogItemService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(summary = "Cadastrar um novo serviço no catálogo", description = "Adiciona um item ao catálogo de serviços do prestador autenticado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
    })
    public ResponseEntity<GetCatalogItemResponse> register(@RequestBody @Valid CreateCatalogItemRequest request, @AuthenticationPrincipal User user) {
        var catalogItem = modelMapper.map(request, CatalogItem.class);

        catalogItem.setProvider(user);

        var createdCatalogItem = service.register(catalogItem);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdCatalogItem.getId())
            .toUri();

        return ResponseEntity.created(location).body(modelMapper.map(createdCatalogItem, GetCatalogItemResponse.class));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROVIDER', 'CLIENT')")
    @Operation(summary = "Buscar serviço por ID", description = "Retorna os detalhes de um serviço específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serviço encontrado"),
        @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
    })
    public ResponseEntity<GetCatalogItemResponse> findById(@PathVariable @NotNull UUID id) {
        var catalogItem = service.findById(id);
        return ResponseEntity.ok(modelMapper.map(catalogItem, GetCatalogItemResponse.class));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(summary = "Remover serviço do catálogo", description = "Remove um serviço do catálogo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Serviço removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
    })
    public ResponseEntity<Void> deleteById(@PathVariable @NotNull UUID id, @AuthenticationPrincipal User loggedUser) {
        service.deleteById(id, loggedUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(summary = "Atualizar serviço do catálogo", description = "Atualiza um serviço do catálogo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
    })
    public ResponseEntity<GetCatalogItemResponse> update(@PathVariable @NotNull UUID id, @RequestBody @Valid CreateCatalogItemRequest request, @AuthenticationPrincipal User loggedUser) {
        var catalogItem = modelMapper.map(request, CatalogItem.class);
        service.update(id, catalogItem, loggedUser);

        return ResponseEntity.ok(modelMapper.map(catalogItem, GetCatalogItemResponse.class));
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @Operation(summary = "Listar serviços do prestador autenticado", description = "Lista os serviços do catálogo do prestador logado, com filtro opcional por nome.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado"),
    })
    @GetMapping("/me")
    public ResponseEntity<Page<GetCatalogItemResponse>> findMyCatalogItems(
            @RequestParam(required = false) String name,
            @AuthenticationPrincipal User user,
            Pageable pageable) {
        Page<CatalogItem> catalogItems = service.findByFilter(user.getId(), null, name, null, pageable);
        return ResponseEntity.ok(catalogItems.map(c -> modelMapper.map(c, GetCatalogItemResponse.class)));
    }

    @PreAuthorize("hasAnyRole('PROVIDER', 'CLIENT')")
    @Operation(summary = "Listar serviços do catálogo", description = "Lista os serviços de catálogo com filtros opcionais de prestador, categoria e nome, suportando paginação.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso"),
    })
    @GetMapping
    public ResponseEntity<Page<GetCatalogItemResponse>> findByFilter(
            @RequestParam(required = false) UUID providerId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String providerName,
            Pageable pageable) {
        Page<CatalogItem> catalogItems = service.findByFilter(providerId, category, name, providerName, pageable);
        return ResponseEntity.ok(catalogItems.map(c -> modelMapper.map(c, GetCatalogItemResponse.class)));
    }
}
