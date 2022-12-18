package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "projectsstates")
public class ProjectsStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprojectsstates")
    private long idProjectsStates; 

    @ManyToOne
    @JoinColumn(name = "idproject", referencedColumnName = "idprojects")
    private ProjectEntity idProject;

    @ManyToOne
    @JoinColumn(name = "idstate", referencedColumnName = "idprojectstatenames")
    private ProjectStateNameEntity idState;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "estimateenddate")
    private Date estimateEndDate;

    @Column(name = "enddate")
    private Date endDate;

    public ProjectsStateEntity() {}

    public ProjectsStateEntity(long idProjectsStates, ProjectEntity idProject, ProjectStateNameEntity idState, Date startDate, Date estimateEndDate, Date endDate) {
        this.idProjectsStates = idProjectsStates;
        this.idProject = idProject;
        this.idState = idState;
        this.startDate = startDate;
        this.estimateEndDate = estimateEndDate;
        this.endDate = endDate;
    }

    public long getIdProjectsStates() {
        return idProjectsStates;
    }

    public void setIdProjectsStates(long idProjectsStates) {
        this.idProjectsStates = idProjectsStates;
    }

    public ProjectEntity getIdProject() {
        return idProject;
    }

    public void setIdProject(ProjectEntity idProject) {
        this.idProject = idProject;
    }

    public ProjectStateNameEntity getIdState() {
        return idState;
    }

    public void setIdState(ProjectStateNameEntity idState) {
        this.idState = idState;
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

}
