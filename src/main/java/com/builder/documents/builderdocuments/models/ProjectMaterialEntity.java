package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "projectmaterials")
public class ProjectMaterialEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprojectmaterials")
    private long idProjectMaterials; 

    @ManyToOne
    @JoinColumn(name = "idproject", referencedColumnName = "idprojects")
    private ProjectEntity idProject;

    @ManyToOne
    @JoinColumn(name = "idmaterial", referencedColumnName = "idmaterials")
    private MaterialEntity idMaterial;

    @Column(name = "estimatecount")
    private int estimateCount;

    @Column(name = "usedcount")
    private int usedCount;

    public ProjectMaterialEntity() {}

    public ProjectMaterialEntity(long idProjectMaterials, ProjectEntity idProject, MaterialEntity idMaterial, int estimateCount, int usedCount) {
        this.idProjectMaterials = idProjectMaterials;
        this.idProject = idProject;
        this.idMaterial = idMaterial;
        this.estimateCount = estimateCount;
        this.usedCount = usedCount;
    }

    public long getIdProjectMaterials() {
        return idProjectMaterials;
    }

    public void setIdProjectMaterials(long idProjectMaterials) {
        this.idProjectMaterials = idProjectMaterials;
    }

    public ProjectEntity getIdProject() {
        return idProject;
    }

    public void setIdProject(ProjectEntity idProject) {
        this.idProject = idProject;
    }

    public MaterialEntity getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(MaterialEntity idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getEstimateCount() {
        return estimateCount;
    }

    public void setEstimateCount(int estimateCount) {
        this.estimateCount = estimateCount;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }
}
