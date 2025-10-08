package net.microgoose.mocknet.auth;

import net.microgoose.mocknet.auth.service.EmailService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailServiceTest {

    private final EmailService emailService = new EmailService();

    @Test
    void isValidEmail_shouldReturnTrue() {
        assertTrue(emailService.isValidEmail("mail@gmail.com"));
        assertTrue(emailService.isValidEmail("mail@yandex.ru"));
        assertTrue(emailService.isValidEmail("mail-1@yandex.ru"));
        assertTrue(emailService.isValidEmail("mail_A-Z-2@yandex.ru"));
    }

    @Test
    void isValidEmail_shouldReturnFalse() {
        assertFalse(emailService.isValidEmail(null));
        assertFalse(emailService.isValidEmail(""));
        assertFalse(emailService.isValidEmail("mail @gmail.com"));
        assertFalse(emailService.isValidEmail("mail@ gmail.com"));
        assertFalse(emailService.isValidEmail("mail@gmail .com"));
        assertFalse(emailService.isValidEmail("mailgmail.com"));
        assertFalse(emailService.isValidEmail("mail@gmailcom"));
    }
}