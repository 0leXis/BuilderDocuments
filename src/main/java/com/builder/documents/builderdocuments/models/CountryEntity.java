package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "countries")
public class CountryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcountries")
    private long idCountries; 

    @Column(name = "name")
    private String name;

    public CountryEntity() {}

    public CountryEntity(long idCountries, String name) {
        this.idCountries = idCountries;
        this.name = name;
    }

    public long getIdCountries() {
        return idCountries;
    }

    public void setIdCountries(long idCountries) {
        this.idCountries = idCountries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
