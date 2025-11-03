package net.sobestime.auth.service;

import lombok.RequiredArgsConstructor;
import net.sobestime.app.exception.ValidationException;
import net.sobestime.auth.dto.AuthTokensDto;
import net.sobestime.auth.dto.LoginRequest;
import net.sobestime.auth.dto.RegistrationRequest;
import net.sobestime.auth.model.UserPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static net.sobestime.auth.config.MessageDictionary.AUTH_FAILED;
import static net.sobestime.auth.config.MessageDictionary.INCORRECT_AUTH_DATA;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserPrincipalService userPrincipalService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthTokensDto register(RegistrationRequest request) {
        // TODO Send verification email
        // TODO password strength
        UserPrincipal userPrincipal = userPrincipalService.createUser(request);
        return jwtService.generateTokenPair(userPrincipal);
    }

    public AuthTokensDto login(LoginRequest request) {
        UserPrincipal userPrincipal = userPrincipalService.getUserByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), userPrincipal.getPassword())) {
            throw new ValidationException(INCORRECT_AUTH_DATA);
        }

        return jwtService.generateTokenPair(userPrincipal);
    }

    public AuthTokensDto login(String refreshToken) {
        if (refreshToken == null || !jwtService.isValid(refreshToken)) {
            throw new ValidationException(AUTH_FAILED);
        }

        String email = jwtService.getEmailFromToken(refreshToken);
        UserPrincipal userPrincipal = userPrincipalService.getUserByEmail(email);

        return jwtService.generateTokenPair(userPrincipal);
    }

}
