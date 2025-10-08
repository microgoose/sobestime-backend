package net.microgoose.mocknet.auth.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.dto.AccessTokenResponse;
import net.microgoose.mocknet.auth.dto.AuthRequest;
import net.microgoose.mocknet.auth.dto.TokenDto;
import net.microgoose.mocknet.auth.service.LoginService;
import net.microgoose.mocknet.auth.service.RegisterService;
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

    private final LoginService loginService;
    private final RegisterService registerService;
    private final TokenCookieService tokenCookieService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody AuthRequest request) {
        TokenDto tokenDto = loginService.login(request);
        ResponseCookie cookie = tokenCookieService.createRefreshTokenCookie(tokenDto.getRefreshToken());

        AccessTokenResponse response = AccessTokenResponse.builder()
            .accessToken(tokenDto.getAccessToken())
            .build();

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AccessTokenResponse> register(@RequestBody AuthRequest request) {
        TokenDto tokenDto = registerService.register(request);
        ResponseCookie cookie = tokenCookieService.createRefreshTokenCookie(tokenDto.getRefreshToken());

        AccessTokenResponse response = AccessTokenResponse.builder()
            .accessToken(tokenDto.getAccessToken())
            .build();

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(response);
    }

}
