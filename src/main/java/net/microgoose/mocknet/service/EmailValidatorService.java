package net.microgoose.mocknet.service;

import org.springframework.stereotype.Service;

@Service
public class EmailValidatorService {
    
    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}