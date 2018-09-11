package br.com.marinete.api.entities;



import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "marinete")
public class Marinete implements Serializable {


    private static final long serialVersionUID = 2683110986427187189L;

    private Long id;
    private String name;
    private Integer ctps;
    private Integer evaluation;
    private String email;
    private List<Service> serviceList;

    public Marinete() {
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

    @Column(name = "ctps", nullable = true)
    public Integer getCtps() {
        return ctps;
    }

    public void setCtps(Integer ctps) {
        this.ctps = ctps;
    }

    @Column(name = "evaluation", nullable = true)
    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "marinete", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }


    @Override
    public String toString() {
        return "Marinete{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Evaluation=" + evaluation +
                '}';
    }
}
