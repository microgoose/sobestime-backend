package net.microgoose.mocknet.service;

import net.microgoose.mocknet.dto.CreateUserRequest;
import net.microgoose.mocknet.dto.UpdateUserRequest;
import net.microgoose.mocknet.factory.TestDataFactoryExtension;
import net.microgoose.mocknet.factory.TestFactory;
import net.microgoose.mocknet.factory.dto.CreateUserRequestFactory;
import net.microgoose.mocknet.factory.dto.UpdateUserRequestFactory;
import net.microgoose.mocknet.factory.model.UserFactory;
import net.microgoose.mocknet.mapper.UserMapper;
import net.microgoose.mocknet.model.User;
import net.microgoose.mocknet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, TestDataFactoryExtension.class})
class UserServiceTest {

    @TestFactory
    private UserFactory userFactory;
    @TestFactory
    private CreateUserRequestFactory createUserFactory;
    @TestFactory
    private UpdateUserRequestFactory updateUserFactory;

    @Mock
    private EmailValidatorService emailValidator;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService service;

    @Test
    void existById_returnsTrue() {
        UUID uuid = UUID.randomUUID();
        when(repository.existsById(uuid)).thenReturn(true);
        assertTrue(service.existById(uuid));
        verify(repository, times(1)).existsById(uuid);
    }

    @Test
    void getAllSessions() {
        User user = userFactory.createPersisted();
        List<User> requests = List.of(user);
        when(repository.findAll()).thenReturn(requests);

        List<User> result = service.getAllUsers();
        verify(repository, times(1)).findAll();
        assertTrue(result.containsAll(requests));
    }

    @Test
    void getUserById_returnsUser() {
        User user = userFactory.createPersisted();
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = service.getUserById(user.getId());
        verify(repository, times(1)).findById(user.getId());
        assertEquals(result, user);
    }

    @Test
    void createUser_shouldCreateUser() {
        CreateUserRequest createUser = createUserFactory.createNew();
        User newUser = userFactory.createNew();
        when(emailValidator.isValidEmail(createUser.getEmail())).thenReturn(true);
        when(repository.existsByEmail(createUser.getEmail())).thenReturn(false);
        when(repository.existsByUsername(createUser.getUsername())).thenReturn(false);
        when(mapper.fromDto(createUser)).thenReturn(newUser);
        when(repository.save(newUser)).thenReturn(newUser);

        User createdUser = service.createUser(createUser);
        verify(repository, times(1)).save(newUser);
        assertEquals(createdUser, newUser);
    }

    @Test
    void updateUser_shouldUpdateUser() {
        UpdateUserRequest updUser = updateUserFactory.createNew();
        User user = userFactory.createPersisted();
        when(emailValidator.isValidEmail(updUser.getEmail())).thenReturn(true);
        when(repository.existsByEmail(updUser.getEmail())).thenReturn(false);
        when(repository.existsByUsername(updUser.getUsername())).thenReturn(false);
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);

        User updatedUser = service.updateUser(updUser);
        verify(repository, times(1)).save(user);
        assertEquals(updatedUser, user);
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        UUID uuid = UUID.randomUUID();
        when(repository.existsById(uuid)).thenReturn(true);
        service.deleteUser(uuid);
    }
}