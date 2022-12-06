package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "documentapprovers")
public class DocumentApproverEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddocumentapprovers")
    private Long idDocumentApprover; 

    @ManyToOne
    @JoinColumn(name = "staff", referencedColumnName = "idstaff")
    private StaffEntity staff;

    @ManyToOne
    @JoinColumn(name = "document", referencedColumnName = "iddocument")
    private DocumentEntity document;

    @Column(name = "approved")
    private boolean approved;

    public DocumentApproverEntity() {}

    public DocumentApproverEntity(Long idDocumentApprover, StaffEntity staff, DocumentEntity document, boolean approved) {
        this.idDocumentApprover = idDocumentApprover;
        this.staff = staff;
        this.document = document;
        this.approved = approved;
    }

    public Long getIdDocumentApprover() {
        return idDocumentApprover;
    }

    public void setIdDocumentApprover(Long idDocumentApprover) {
        this.idDocumentApprover = idDocumentApprover;
    }

    public StaffEntity getStaff() {
        return staff;
    }

    public void setStaff(StaffEntity staff) {
        this.staff = staff;
    }

    public DocumentEntity getDocument() {
        return document;
    }

    public void setDocument(DocumentEntity document) {
        this.document = document;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

}
