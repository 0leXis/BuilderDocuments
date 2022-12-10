package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "documents")
public class DocumentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddocument")
    private long idDocument; 

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator", referencedColumnName = "idstaff")
    private StaffEntity creator;

    @ManyToOne
    @JoinColumn(name = "assignee", referencedColumnName = "idstaff")
    private StaffEntity assignee;

    @ManyToOne
    @JoinColumn(name = "template", referencedColumnName = "iddocumenttemplates")
    private DocumentTemplateEntity template;

    @Column(name = "datecreated")
    private Date dateCreated;

    @Column(name = "datemodified")
    private Date dateModified;

    @Column(name = "description")
    @Basic(fetch = FetchType.LAZY)
    private String description;

    @Column(name = "hash")
    private String hash;

    @Column(name = "path")
    private String path;

    @Column(name = "isformalized")
    private boolean isFormalized;

    public DocumentEntity() {}

    public DocumentEntity(long idDocument, String name, StaffEntity creator, StaffEntity assignee, DocumentTemplateEntity template, Date dateCreated, Date dateModified, String description, String hash, String path, boolean isFormalized) {
        this.idDocument = idDocument;
        this.name = name;
        this.creator = creator;
        this.assignee = assignee;
        this.template = template;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.description = description;
        this.hash = hash;
        this.path = path;
        this.isFormalized = isFormalized;
    }

    public long getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(long idDocument) {
        this.idDocument = idDocument;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StaffEntity getCreator() {
        return creator;
    }

    public void setCreator(StaffEntity creator) {
        this.creator = creator;
    }

    public DocumentTemplateEntity getTemplate() {
        return template;
    }

    public void setTemplate(DocumentTemplateEntity template) {
        this.template = template;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public StaffEntity getAssignee() {
        return assignee;
    }

    public void setAssignee(StaffEntity assignee) {
        this.assignee = assignee;
    }

    public boolean isFormalized() {
        return isFormalized;
    }

    public void setFormalized(boolean isFormalized) {
        this.isFormalized = isFormalized;
    }
}
