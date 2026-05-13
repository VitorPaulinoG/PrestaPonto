package br.senac.ead.prestaponto.api.controller;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.senac.ead.prestaponto.api.dto.CreateUserRequest;
import br.senac.ead.prestaponto.api.entity.User;
import br.senac.ead.prestaponto.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Operation(
        summary = "Registrar um novo usuário", 
        description = "Registra um novo usuário com as informações fornecidas."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "409", description = "Usuário já existe")
    })
    @PostMapping
    public ResponseEntity<?> register(@RequestBody CreateUserRequest request) {

        User savedUser = userService.register(modelMapper.map(request, User.class));
        
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest() 
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
                
        return ResponseEntity.created(uri).build();
    }


}
