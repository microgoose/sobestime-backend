package net.microgoose.mocknet.auth.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.app.exception.NotFoundException;
import net.microgoose.mocknet.app.exception.ValidationException;
import net.microgoose.mocknet.auth.dto.RegistrationRequest;
import net.microgoose.mocknet.auth.mapper.UserPrincipalMapper;
import net.microgoose.mocknet.auth.model.Role;
import net.microgoose.mocknet.auth.model.UserPrincipal;
import net.microgoose.mocknet.auth.repository.UserPrincipalRepository;
import net.microgoose.mocknet.intermediate.dto.UserRegisterEvent;
import net.microgoose.mocknet.intermediate.topic.UserTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static net.microgoose.mocknet.auth.config.MessageDictionary.*;

@Service
@RequiredArgsConstructor
public class UserPrincipalService {

    private final UserPrincipalRepository repository;
    private final UserPrincipalMapper mapper;
    private final RoleService roleService;
    private final UserTopic userTopic;

    public UserPrincipal getUserByEmail(String email) {
        return repository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    @Transactional
    public UserPrincipal createUser(RegistrationRequest request) {
        if (!StringUtils.hasText(request.getPassword()))
            throw new ValidationException(PASSWORD_NOT_SPECIFIED);
        if (repository.existsByEmail(request.getEmail()))
            throw new ValidationException(EMAIL_ALREADY_EXIST);

        UserPrincipal userPrincipal = mapper.fromDto(request);
        userPrincipal.setEnabled(true);

        Role userRole = roleService.findByName("ROLE_USER");
        userPrincipal.getRoles().add(userRole);

        userPrincipal = repository.save(userPrincipal);
        userTopic.sendUserRegisterEvent(UserRegisterEvent.builder()
            .id(userPrincipal.getId())
            .username(userPrincipal.getUsername())
            .build());

        return userPrincipal;
    }

}