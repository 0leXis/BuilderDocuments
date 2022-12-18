package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "materialprovider")
public class MaterialProviderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmaterialprovider")
    private long idMaterialProvider; 

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "idcountries")
    private CountryEntity country;

    public MaterialProviderEntity() {}

    public MaterialProviderEntity(long idMaterialProvider, String name, CountryEntity country) {
        this.idMaterialProvider = idMaterialProvider;
        this.name = name;
        this.country = country;
    }

    public long getIdMaterialProvider() {
        return idMaterialProvider;
    }

    public void setIdMaterialProvider(long idMaterialProvider) {
        this.idMaterialProvider = idMaterialProvider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }
}
