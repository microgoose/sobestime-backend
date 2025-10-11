package net.microgoose.mocknet.auth;

import net.microgoose.mocknet.auth.util.EmailValidationUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidationUtilTest {

    @Test
    void isValidEmail_shouldReturnTrue() {
        assertTrue(EmailValidationUtil.isValidEmail("mail@gmail.com"));
        assertTrue(EmailValidationUtil.isValidEmail("mail@yandex.ru"));
        assertTrue(EmailValidationUtil.isValidEmail("mail-1@yandex.ru"));
        assertTrue(EmailValidationUtil.isValidEmail("mail_A-Z-2@yandex.ru"));
    }

    @Test
    void isValidEmail_shouldReturnFalse() {
        assertFalse(EmailValidationUtil.isValidEmail(null));
        assertFalse(EmailValidationUtil.isValidEmail(""));
        assertFalse(EmailValidationUtil.isValidEmail("mail @gmail.com"));
        assertFalse(EmailValidationUtil.isValidEmail("mail@ gmail.com"));
        assertFalse(EmailValidationUtil.isValidEmail("mail@gmail .com"));
        assertFalse(EmailValidationUtil.isValidEmail("mailgmail.com"));
        assertFalse(EmailValidationUtil.isValidEmail("mail@gmailcom"));
    }
}