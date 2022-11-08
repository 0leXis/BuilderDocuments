package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "documenttemplates")
public class DocumentTemplateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddocumenttemplates")
    private long idDocumentTemplates; 

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    public DocumentTemplateEntity() {}

    public DocumentTemplateEntity(long idDocumentTemplates, String name, String path) {
        this.idDocumentTemplates = idDocumentTemplates;
        this.name = name;
        this.path = path;
    }

    public long getIdDocumentTemplates() {
        return idDocumentTemplates;
    }

    public void setIdDocumentTemplates(long idDocumentTemplates) {
        this.idDocumentTemplates = idDocumentTemplates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
