package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "positions")
public class PositionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpositions")
    private long idPositions; 

    @Column(name = "name")
    private String name;

    public PositionEntity() {}

    public PositionEntity(long idPositions, String name) {
        this.idPositions = idPositions;
        this.name = name;
    }

    public long getIdPositions() {
        return idPositions;
    }

    public void setIdPositions(long idPositions) {
        this.idPositions = idPositions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
