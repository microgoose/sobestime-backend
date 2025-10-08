package net.microgoose.mocknet.auth.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.NotFoundException;
import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.auth.dto.AuthRequest;
import net.microgoose.mocknet.auth.mapper.AuthUserMapper;
import net.microgoose.mocknet.auth.model.AuthUser;
import net.microgoose.mocknet.auth.model.Role;
import net.microgoose.mocknet.auth.repository.AuthUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final EmailService emailValidator;
    private final AuthUserRepository repository;
    private final AuthUserMapper mapper;
    private final AuthRequestService authRequestService;
    private final RoleService roleService;

    public boolean existById(UUID id) {
        return repository.existsById(id);
    }

    public AuthUser getUserByEmail(String email) {
        return repository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + email));
    }

    @Transactional
    public AuthUser createUser(AuthRequest request) {
        authRequestService.validate(request);
        validateUserEmail(request.getEmail());
        AuthUser authUser = mapper.fromDto(request);

        Role userRole = roleService.findByName("ROLE_USER");
        authUser.getRoles().add(userRole);

        return repository.save(authUser);
    }

    private void validateUserEmail(String email) {
        if (!emailValidator.isValidEmail(email))
            throw new ValidationException("Некорректный email: " + email);
        if (repository.existsByEmail(email))
            throw new ValidationException("Email уже существует: " + email);
    }

}