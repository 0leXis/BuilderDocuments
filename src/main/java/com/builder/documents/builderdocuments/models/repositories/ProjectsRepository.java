package com.builder.documents.builderdocuments.models.repositories;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.builder.documents.builderdocuments.models.ProjectEntity;

@Repository
public interface ProjectsRepository extends JpaRepository<ProjectEntity, Long> {
    
}
