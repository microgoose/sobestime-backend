package net.microgoose.mocknet.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.dto.CreateUserRequest;
import net.microgoose.mocknet.dto.UpdateUserRequest;
import net.microgoose.mocknet.dto.UserDto;
import net.microgoose.mocknet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody CreateUserRequest user) {
        return service.createUser(user);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable UUID id) {
        return service.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@RequestBody UpdateUserRequest request) {
        return service.updateUser(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        service.deleteUser(id);
    }
}
