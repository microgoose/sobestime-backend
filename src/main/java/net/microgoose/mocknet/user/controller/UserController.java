package net.microgoose.mocknet.user.controller;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.user.dto.CreateUserRequest;
import net.microgoose.mocknet.user.dto.UpdateUserRequest;
import net.microgoose.mocknet.user.dto.UserDto;
import net.microgoose.mocknet.user.mapper.UserMapper;
import net.microgoose.mocknet.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return mapper.toDto(service.getAllUsers());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody CreateUserRequest user) {
        return mapper.toDto(service.createUser(user));
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable UUID id) {
        return mapper.toDto(service.getUserById(id));
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@RequestBody UpdateUserRequest request) {
        return mapper.toDto(service.updateUser(request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
       service.deleteUser(id);
    }
}
