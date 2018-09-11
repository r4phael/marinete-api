package br.com.marinete.api.utils;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static junit.framework.TestCase.*;

public class PasswordUtilsTest {

    public static final String PASSWORD = "123456";
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Test
    public void testPasswordNull() throws Exception{
        assertNull(PasswordUtils.genBcrypt(null));
    }

    @Test
    public void testGenPassword() throws Exception{
        String hash = PasswordUtils.genBcrypt(PASSWORD);
        assertTrue(bCryptPasswordEncoder.matches(PASSWORD,hash));
    }
}
