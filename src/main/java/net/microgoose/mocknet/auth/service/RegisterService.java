package net.microgoose.mocknet.auth.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.auth.dto.AuthRequest;
import net.microgoose.mocknet.auth.dto.AuthTokensDto;
import net.microgoose.mocknet.auth.model.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final AuthUserService authUserService;
    private final JwtService jwtService;

    public AuthTokensDto register(AuthRequest request) {
        // TODO Send verification email
        UserPrincipal userPrincipal = authUserService.createUser(request);
        return jwtService.generateTokenPair(userPrincipal);
    }

}
