package net.microgoose.mocknet.auth.service;

import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.auth.dto.AuthRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthRequestService {

    public void validate(AuthRequest request) throws ValidationException {
        if (!StringUtils.hasText(request.getEmail()))
            throw new ValidationException("Не указан email");
        if (!StringUtils.hasText(request.getPassword()))
            throw new ValidationException("Не указан пароль");
    }
}
