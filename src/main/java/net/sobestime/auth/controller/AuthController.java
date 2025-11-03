package net.sobestime.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.sobestime.auth.dto.AuthResponse;
import net.sobestime.auth.dto.AuthTokensDto;
import net.sobestime.auth.dto.LoginRequest;
import net.sobestime.auth.dto.RegistrationRequest;
import net.sobestime.auth.service.AuthService;
import net.sobestime.auth.service.TokenCookieService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
    private final TokenCookieService tokenCookieService;

    // TODO password recovery
    // TODO disable/ban user

    @Operation(summary = "Выйти", description = "Выход используя куки")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, tokenCookieService.createExpiredRefreshTokenCookie().toString())
            .build();
    }

    @Operation(summary = "Войти")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthTokensDto tokenDto = authService.login(request);
        ResponseCookie cookie = tokenCookieService.createRefreshTokenCookie(tokenDto.getRefreshToken());

        AuthResponse response = AuthResponse.builder()
            .accessToken(tokenDto.getAccessToken())
            .build();

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(response);
    }

    @Operation(summary = "Регистрация", description = "Регистрация по email/password")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegistrationRequest request) {
        AuthTokensDto tokenDto = authService.register(request);
        ResponseCookie cookie = tokenCookieService.createRefreshTokenCookie(tokenDto.getRefreshToken());

        AuthResponse response = AuthResponse.builder()
            .accessToken(tokenDto.getAccessToken())
            .build();

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(response);
    }

}
