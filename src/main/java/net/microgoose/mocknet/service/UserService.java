package net.microgoose.mocknet.service;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateUserRequest;
import net.microgoose.mocknet.dto.UpdateUserRequest;
import net.microgoose.mocknet.exception.NotFoundException;
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

    public boolean existById(UUID id) {
        return repository.existsById(id);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + id));
    }

    @Transactional
    public User createUser(CreateUserRequest request) {
        validateUserEmail(request.getEmail());
        validateUsername(request.getUsername());

        User user = mapper.fromDto(request);
        user.setPasswordHash("password");
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        return repository.save(user);
    }

    @Transactional
    public User updateUser(UpdateUserRequest request) {
        validateUserEmail(request.getEmail());
        validateUsername(request.getUsername());

        User existing = getUserById(request.getId());
        existing.setUsername(request.getUsername());
        existing.setEmail(request.getEmail());
        existing.setUpdatedAt(Instant.now());

        return repository.save(existing);
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (existById(id)) {
            repository.deleteById(id);
            return;
        }

        throw new NotFoundException("Пользователь не найден");
    }

    private void validateUserEmail(String email) {
        if (!emailValidator.isValidEmail(email))
            throw new ValidationException("Некорректный email: " + email);
        if (repository.existsByEmail(email))
            throw new ValidationException("Email уже существует: " + email);
    }

    private void validateUsername(String username) {
        if (repository.existsByUsername(username))
            throw new ValidationException("Имя пользователя уже существует: " + username);
    }
}