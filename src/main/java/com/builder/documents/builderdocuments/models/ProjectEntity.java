package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
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
    @JoinColumn(name = "order", referencedColumnName = "idorders")
    private OrderEntity order;

    @OneToOne
    @JoinColumn(name = "currentstate", referencedColumnName = "idProjectsStates")
    private ProjectsStateEntity currentState;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "estimateenddate")
    private Date estimateEndDate;

    @Column(name = "enddate")
    private Date endDate;

    @Column(name = "cost")
    private BigDecimal cost;

    public ProjectEntity() {}

    public ProjectEntity(long idProjects, String name, OrderEntity order, Date startDate, Date estimateEndDate, Date endDate, BigDecimal cost) {
        this.idProjects = idProjects;
        this.name = name;
        this.order = order;
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
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
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
