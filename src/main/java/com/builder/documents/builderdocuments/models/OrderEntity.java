package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idorders")
    private long idOrders; 

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "client", referencedColumnName = "idclients")
    private ClientEntity client;

    public OrderEntity() {}

    public OrderEntity(long idOrders, String name, String description, ClientEntity client) {
        this.idOrders = idOrders;
        this.description = description;
        this.name = name;
        this.client = client;
    }

    public long getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(long idOrders) {
        this.idOrders = idOrders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }
}
