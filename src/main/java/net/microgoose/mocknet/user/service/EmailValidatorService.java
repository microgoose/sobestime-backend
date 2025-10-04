package net.microgoose.mocknet.user.service;

import org.springframework.stereotype.Service;

@Service
public class EmailValidatorService {
    
    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+\\.[A-Za-z0-9+_.-]+$");
    }
}