package br.com.marinete.api.dtos;

import br.com.marinete.api.entities.Service;
import br.com.marinete.api.enums.ProfileEnum;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

public class UserDto {

    private Long id;
    private String name;
    private String email;
    private Optional<String> password = Optional.empty();
    private String cpf;
    //private ProfileEnum profile;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "The name can't be empty")
    @Length(min = 2, max = 200, message = "The name must have between 3 and 200 characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "Email can not be empty")
    @Length(min = 5, max = 100, message = "Email must have between 5 and 100 characteres")
    @Email(message="Invalid email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Optional<String> getPassword() {
        return password;
    }

    public void setPassword(Optional<String> password) {
        this.password = password;
    }

    @NotEmpty(message = "The CPF can not be empty")
    @CPF(message = "Invalid CPF")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /*
    @Digits(integer = 2, fraction = 2, message = "Profile can not be empty")
    public ProfileEnum getProfile() {
        return profile;
    }

    public void setProfile(ProfileEnum profile) {
        this.profile = profile;
    }
    */

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cpf='" + cpf + '\'' +
                //", profile=" + profile +
                '}';
    }
}
