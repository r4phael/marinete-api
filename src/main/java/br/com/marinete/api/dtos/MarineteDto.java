package br.com.marinete.api.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MarineteDto {

    private Long id;
    private String name;
    private int ctps;
    private int evaluation;
    private String email;


    public MarineteDto() {
    }

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
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    @Digits(integer = 10, fraction = 2, message = "CTPS can not be empty")
    public int getCtps() {
        return ctps;
    }

    public void setCtps(int ctps) {
        this.ctps = ctps;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public String toString() {
        return "MarineteDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ctps=" + ctps +
                ", evaluation=" + evaluation +
                ", email='" + email + '\'' +
                '}';
    }
}
