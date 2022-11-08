package com.builder.documents.builderdocuments.models.repositories;

import com.builder.documents.builderdocuments.models.DocumentEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsRepository extends JpaRepository<DocumentEntity, Long> {
}
