package com.builder.documents.builderdocuments.models.repositories;

import com.builder.documents.builderdocuments.models.ProjectEntity;
import com.builder.documents.builderdocuments.models.ProjectMaterialEntity;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMaterialRepository extends JpaRepository<ProjectMaterialEntity, Long> {
    List<ProjectMaterialEntity> findByIdProject(ProjectEntity project);
}
