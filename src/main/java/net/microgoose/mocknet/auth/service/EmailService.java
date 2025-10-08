package net.microgoose.mocknet.auth.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+\\.[A-Za-z0-9+_.-]+$");
    }
}