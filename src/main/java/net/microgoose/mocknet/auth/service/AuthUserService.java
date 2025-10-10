package net.microgoose.mocknet.auth.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.NotFoundException;
import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.auth.dto.AuthRequest;
import net.microgoose.mocknet.auth.mapper.UserPrincipalMapper;
import net.microgoose.mocknet.auth.model.Role;
import net.microgoose.mocknet.auth.model.UserPrincipal;
import net.microgoose.mocknet.auth.repository.UserPrincipalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final EmailService emailValidator;
    private final UserPrincipalRepository repository;
    private final UserPrincipalMapper mapper;
    private final AuthRequestService authRequestService;
    private final RoleService roleService;

    public UserPrincipal getUserByEmail(String email) {
        return repository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + email));
    }

    @Transactional
    public UserPrincipal createUser(AuthRequest request) {
        authRequestService.validate(request);
        validateUserEmail(request.getEmail());
        UserPrincipal userPrincipal = mapper.fromDto(request);

        Role userRole = roleService.findByName("ROLE_USER");
        userPrincipal.getRoles().add(userRole);

        return repository.save(userPrincipal);
    }

    private void validateUserEmail(String email) {
        if (!emailValidator.isValidEmail(email))
            throw new ValidationException("Некорректный email: " + email);
        if (repository.existsByEmail(email))
            throw new ValidationException("Email уже существует: " + email);
    }

}