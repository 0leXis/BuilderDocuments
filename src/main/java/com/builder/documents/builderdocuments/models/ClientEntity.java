package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clients")
public class ClientEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idclients")
    private long idClients; 

    @Column(name = "name")
    private String name;

    public ClientEntity() {}

    public ClientEntity(long idClients, String name) {
        this.idClients = idClients;
        this.name = name;
    }

    public long getIdClients() {
        return idClients;
    }

    public void setIdClients(long idClients) {
        this.idClients = idClients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
