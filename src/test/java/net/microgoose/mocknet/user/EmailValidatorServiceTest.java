package net.microgoose.mocknet.user;

import net.microgoose.mocknet.user.service.EmailValidatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidatorServiceTest {

    private final EmailValidatorService emailValidatorService = new EmailValidatorService();

    @Test
    void isValidEmail_shouldReturnTrue() {
        assertTrue(emailValidatorService.isValidEmail("mail@gmail.com"));
        assertTrue(emailValidatorService.isValidEmail("mail@yandex.ru"));
        assertTrue(emailValidatorService.isValidEmail("mail-1@yandex.ru"));
        assertTrue(emailValidatorService.isValidEmail("mail_A-Z-2@yandex.ru"));
    }

    @Test
    void isValidEmail_shouldReturnFalse() {
        assertFalse(emailValidatorService.isValidEmail(null));
        assertFalse(emailValidatorService.isValidEmail(""));
        assertFalse(emailValidatorService.isValidEmail("mail @gmail.com"));
        assertFalse(emailValidatorService.isValidEmail("mail@ gmail.com"));
        assertFalse(emailValidatorService.isValidEmail("mail@gmail .com"));
        assertFalse(emailValidatorService.isValidEmail("mailgmail.com"));
        assertFalse(emailValidatorService.isValidEmail("mail@gmailcom"));
    }
}