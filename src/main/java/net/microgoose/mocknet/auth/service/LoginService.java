package net.microgoose.mocknet.auth.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.auth.dto.AuthRequest;
import net.microgoose.mocknet.auth.dto.TokenDto;
import net.microgoose.mocknet.auth.model.AuthUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthUserService authUserService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthRequestService authRequestService;

    public TokenDto login(AuthRequest request) {
        authRequestService.validate(request);
        AuthUser authUser = authUserService.getUserByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), authUser.getPassword())) {
            throw new ValidationException("Неверный email или пароль");
        }

        return jwtService.generateTokenPair(authUser);
    }

}
