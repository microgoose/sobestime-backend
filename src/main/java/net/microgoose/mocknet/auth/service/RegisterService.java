package net.microgoose.mocknet.auth.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.dto.AuthRequest;
import net.microgoose.mocknet.auth.dto.TokenDto;
import net.microgoose.mocknet.auth.model.AuthUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final AuthUserService authUserService;
    private final JwtService jwtService;

    public TokenDto register(AuthRequest request) {
        // TODO Send verification email
        AuthUser authUser = authUserService.createUser(request);
        return jwtService.generateTokenPair(authUser);
    }

}
