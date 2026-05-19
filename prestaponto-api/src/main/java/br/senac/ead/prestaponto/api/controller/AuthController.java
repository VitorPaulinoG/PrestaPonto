package br.senac.ead.prestaponto.api.controller;

import br.senac.ead.prestaponto.api.dto.request.LoginRequestDTO;
import br.senac.ead.prestaponto.api.dto.response.ApiResponse;
import br.senac.ead.prestaponto.api.dto.response.LoginResponseDTO;
import br.senac.ead.prestaponto.api.entity.User;
import br.senac.ead.prestaponto.api.security.TokenService;
import br.senac.ead.prestaponto.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthController(PasswordEncoder passwordEncoder,
                          TokenService tokenService,
                          UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        User user = userService.findByEmail(dto.email())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(dto.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, null, "Invalid credentials"));
        }

        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        new LoginResponseDTO(user.getName(), token),
                        "Login successful"
                )
        );
    }

}
