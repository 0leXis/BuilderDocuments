package com.builder.documents.builderdocuments.models;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "projects")
public class ProjectEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprojects")
    private long idProjects; 

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "orderid", referencedColumnName = "idorders")
    private OrderEntity orderId;

    @OneToOne
    @JoinColumn(name = "currentstate", referencedColumnName = "idProjectsStates")
    private ProjectsStateEntity currentState;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "startdate")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "estimateenddate")
    private Date estimateEndDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "enddate")
    private Date endDate;

    @Column(name = "cost")
    private BigDecimal cost;

    public ProjectEntity() {}

    public ProjectEntity(long idProjects, String name, OrderEntity orderId, Date startDate, Date estimateEndDate, Date endDate, BigDecimal cost) {
        this.idProjects = idProjects;
        this.name = name;
        this.orderId = orderId;
        this.startDate = startDate;
        this.estimateEndDate = estimateEndDate;
        this.endDate = endDate;
        this.cost = cost;
    }

    public long getIdProjects() {
        return idProjects;
    }

    public void setIdProjects(long idProjects) {
        this.idProjects = idProjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderEntity getOrder() {
        return orderId;
    }

    public void setOrder(OrderEntity orderId) {
        this.orderId = orderId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEstimateEndDate() {
        return estimateEndDate;
    }

    public void setEstimateEndDate(Date estimateEndDate) {
        this.estimateEndDate = estimateEndDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
