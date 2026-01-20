package net.sobestime.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.sobestime.auth.dto.AuthTokensDto;
import net.sobestime.auth.dto.LoginRequest;
import net.sobestime.auth.dto.RefreshTokensRequest;
import net.sobestime.auth.dto.RegistrationRequest;
import net.sobestime.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Авторизация")
public class AuthController {

    private final AuthService authService;

    // TODO password recovery
    // TODO disable/ban user

    @Operation(summary = "Обновить токен", description = "Обновление токенов")
    @PostMapping("/refresh")
    public AuthTokensDto refresh(@RequestBody @Valid RefreshTokensRequest request) {
        return authService.login(request.getRefreshToken());
    }

    @Operation(summary = "Регистрация", description = "Регистрация по email/password")
    @PostMapping("/register")
    public AuthTokensDto register(@RequestBody @Valid RegistrationRequest request) {
        return authService.register(request);
    }

    @Operation(summary = "Войти")
    @PostMapping("/login")
    public AuthTokensDto login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "Выйти", description = "Выход")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
