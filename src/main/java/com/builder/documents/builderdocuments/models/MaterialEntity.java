package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "materials")
public class MaterialEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmaterials")
    private long idMaterials; 

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "provider", referencedColumnName = "idmaterialprovider")
    private MaterialProviderEntity provider;

    public MaterialEntity() {}

    public MaterialEntity(long idMaterials, String name, BigDecimal price, MaterialProviderEntity provider) {
        this.idMaterials = idMaterials;
        this.name = name;
        this.price = price;
        this.provider = provider;
    }

    public long getIdMaterials() {
        return idMaterials;
    }

    public void setIdMaterials(long idMaterials) {
        this.idMaterials = idMaterials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MaterialProviderEntity getProvider() {
        return provider;
    }

    public void setProvider(MaterialProviderEntity provider) {
        this.provider = provider;
    }

    
}
