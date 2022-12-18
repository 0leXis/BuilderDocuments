package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "projectstatenames")
public class ProjectStateNameEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprojectstatenames")
    private long idProjectStateNames; 

    @Column(name = "name")
    private String name;

    public ProjectStateNameEntity() {}

    public ProjectStateNameEntity(long idProjectStateNames, String name) {
        this.idProjectStateNames = idProjectStateNames;
        this.name = name;
    }

    public long getIdProjectStateNames() {
        return idProjectStateNames;
    }

    public void setIdProjectStateNames(long idProjectStateNames) {
        this.idProjectStateNames = idProjectStateNames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
