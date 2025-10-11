package net.microgoose.mocknet.auth.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.dto.AuthRequest;
import net.microgoose.mocknet.auth.dto.AuthResponse;
import net.microgoose.mocknet.auth.dto.AuthTokensDto;
import net.microgoose.mocknet.auth.service.AuthService;
import net.microgoose.mocknet.auth.service.TokenCookieService;
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
public class AuthController {

    private final AuthService authService;
    private final TokenCookieService tokenCookieService;

    // TODO logout
    // TODO password recovery
    // TODO disable/ban user

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthTokensDto tokenDto = authService.login(request);
        ResponseCookie cookie = tokenCookieService.createRefreshTokenCookie(tokenDto.getRefreshToken());

        AuthResponse response = AuthResponse.builder()
            .accessToken(tokenDto.getAccessToken())
            .build();

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
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
