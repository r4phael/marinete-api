package br.com.marinete.api.security.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDto {

    private String email;
    private String password;

    public JwtAuthenticationDto() {
    }

    @NotEmpty(message = "Email can not be empty.")
    @Email(message = "Email invalid.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "password can not be empty")
    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    @Override
    public String toString() {
        return "JwtAuthenticationRequestDto [email=" + email + ", password=" + password + "]";
    }

}
