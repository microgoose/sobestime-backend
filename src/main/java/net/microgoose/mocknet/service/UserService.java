package net.microgoose.mocknet.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateUserRequest;
import net.microgoose.mocknet.dto.UpdateUserRequest;
import net.microgoose.mocknet.dto.UserDto;
import net.microgoose.mocknet.exception.ValidationException;
import net.microgoose.mocknet.mapper.UserMapper;
import net.microgoose.mocknet.model.User;
import net.microgoose.mocknet.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EmailValidatorService emailValidator;
    private final UserRepository repository;
    private final UserMapper mapper;

    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        if (!emailValidator.isValidEmail(request.getEmail()))
            throw new ValidationException("Некорректный email: " + request.getEmail());
        if (repository.existsByEmail(request.getEmail()))
            throw new ValidationException("Email уже существует: " + request.getEmail());
        if (repository.existsByUsername(request.getUsername()))
            throw new ValidationException("Имя пользователя уже существует: " + request.getUsername());

        User user = mapper.fromDto(request);
        user.setPasswordHash("password");
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        return mapper.toDto(repository.save(user));
    }

    public List<UserDto> getAllUsers() {
        return mapper.toDto(repository.findAll());
    }

    public UserDto getUserById(UUID id) {
        return mapper.toDto(repository.findById(id)
            .orElseThrow(() -> new ValidationException("Пользователь не найден: " + id)));
    }

    public boolean existById(UUID id) {
        return repository.existsById(id);
    }

    @Transactional
    public UserDto updateUser(UpdateUserRequest user) {
        User existing = repository.findById(user.getId())
            .orElseThrow(() -> new ValidationException("Пользователь не найден: " + user.getId()));

        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setUpdatedAt(Instant.now());
        return mapper.toDto(repository.save(existing));
    }

    @Transactional
    public void deleteUser(UUID id) {
        repository.deleteById(id);
    }
}