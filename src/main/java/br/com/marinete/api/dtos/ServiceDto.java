package br.com.marinete.api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class ServiceDto {

    //@JsonProperty("id")
    private Optional<Long> id = Optional.empty();
    private BigDecimal price;
    private int evaluation;
    private String comments;
    private String localization;
    private String serviceDate;
    private String status;
    private Long marineteId;
    private Long userId;

    public ServiceDto() {
    }

    //@JsonIgnore
    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public Long getMarineteId() {
        return marineteId;
    }

    public void setMarineteId(Long marineteId) {
        this.marineteId = marineteId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @NotEmpty(message = "Date can not be empty.")
    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ServiceDto{" +
                "id=" + id +
                ", price=" + price +
                ", evaluation=" + evaluation +
                ", comments='" + comments + '\'' +
                ", localization='" + localization + '\'' +
                ", serviceDate=" + serviceDate +
                ", status='" + status + '\'' +
                ", marineteId=" + marineteId +
                ", userId=" + userId +
                '}';
    }
}
