package com.builder.documents.builderdocuments.models.repositories;

import com.builder.documents.builderdocuments.models.DocumentApproverEntity;
import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.StaffEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DocumentApproversRepository extends JpaRepository<DocumentApproverEntity, Long> {
    List<DocumentApproverEntity> findByDocument(DocumentEntity document);
    @Transactional
    void deleteByDocument(DocumentEntity document);
    Optional<DocumentApproverEntity> findByDocumentAndStaff(DocumentEntity document, StaffEntity staff);
}
