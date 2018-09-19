package br.com.marinete.api.entities;

import br.com.marinete.api.enums.ProfileEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User implements Serializable {


    private static final long serialVersionUID = -5419625763845982702L;

    private Long id;
    private String name;
    private String email;
    private String password;
    private String cpf;
    private ProfileEnum profile;
    private List<Service> serviceList;

    public User(){
    }

    public User(String name, String email, String password, String cpf, ProfileEnum profile) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.profile = profile;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "cpf", nullable = false)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "profile", nullable = false)
    public ProfileEnum getProfile() {
        return profile;
    }

    public void setProfile(ProfileEnum profile) {
        this.profile = profile;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cpf='" + cpf + '\'' +
                ", profile=" + profile +
                ", serviceList=" + serviceList +
                '}';
    }
}
