package net.microgoose.mocknet.auth.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.auth.dto.AuthRequest;
import net.microgoose.mocknet.auth.dto.AuthTokensDto;
import net.microgoose.mocknet.auth.model.UserPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserPrincipalService userPrincipalService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthTokensDto register(AuthRequest request) {
        // TODO Send verification email
        validate(request);
        UserPrincipal userPrincipal = userPrincipalService.createUser(request);
        return jwtService.generateTokenPair(userPrincipal);
    }

    public AuthTokensDto login(AuthRequest request) {
        validate(request);
        UserPrincipal userPrincipal = userPrincipalService.getUserByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), userPrincipal.getPassword())) {
            throw new ValidationException("Неверный email или пароль");
        }

        return jwtService.generateTokenPair(userPrincipal);
    }

    public AuthTokensDto login(String refreshToken) {
        if (refreshToken == null || !jwtService.isValid(refreshToken)) {
            throw new ValidationException("Не удалось аутентифицировать пользователя");
        }

        String email = jwtService.getEmailFromToken(refreshToken);
        UserPrincipal userPrincipal = userPrincipalService.getUserByEmail(email);

        return jwtService.generateTokenPair(userPrincipal);
    }

    public void validate(AuthRequest request) throws ValidationException {
        if (!StringUtils.hasText(request.getEmail()))
            throw new ValidationException("Не указан email");
        if (!StringUtils.hasText(request.getPassword()))
            throw new ValidationException("Не указан пароль");
    }
}
