package com.builder.documents.builderdocuments.models.repositories;

import com.builder.documents.builderdocuments.models.DocumentTemplateEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTemplateRepository extends JpaRepository<DocumentTemplateEntity, Long> {
    Optional<DocumentTemplateEntity> findByName(String name);
}
