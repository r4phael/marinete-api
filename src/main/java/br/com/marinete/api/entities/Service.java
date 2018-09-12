package br.com.marinete.api.entities;

import br.com.marinete.api.enums.StatusEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "service")
public class Service implements Serializable {

    private static final long serialVersionUID = 5667987769672958281L;

    private Long id;
    private BigDecimal price;
    private int evaluation;
    private String comments;
    private StatusEnum status;
    private Date createdDate;
    private Date updateDate;
    private String localization;
    private Date serviceDate;
    private Marinete marinete;
    private User user;


    public Service(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @Column (name="price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column (name="evaluation",  nullable = true)
    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    @Column (name="comments",  nullable = true)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "status",  nullable = false)
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Column(name = "created_date",  nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "update_date",  nullable = false)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "localization", nullable = false)
    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Marinete getMarinete() {
        return marinete;
    }

    public void setMarinete(Marinete marinete) {
        this.marinete = marinete;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @PreUpdate
    public void preUpdate(){
        updateDate = new Date();
    }

    @PrePersist
    public void prePersist(){
        final Date currentDate = new Date();
        createdDate = currentDate;
        updateDate = currentDate;
    }

    @Column(name = "service_date", nullable = false)
    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", price=" + price +
                ", evaluation=" + evaluation +
                ", comments='" + comments + '\'' +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", updateDate=" + updateDate +
                ", localization='" + localization + '\'' +
                ", serviceDate=" + serviceDate +
                ", marinete=" + marinete +
                ", user=" + user +
                '}';
    }
}
