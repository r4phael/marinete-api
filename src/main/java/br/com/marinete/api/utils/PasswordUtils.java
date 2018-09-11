package br.com.marinete.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {


    private  static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

    public PasswordUtils(){

    }

    /**
     * Generate a has code using BCrypt
     *
     * @Param password
     * @return String
     *
     */

    public static String genBcrypt(String password){
        if (password == null){
            return password;
        }

        log.info("Generating password with BCrypt");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }


}
