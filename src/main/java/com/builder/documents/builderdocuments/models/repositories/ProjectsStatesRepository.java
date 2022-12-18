package com.builder.documents.builderdocuments.models.repositories;

import com.builder.documents.builderdocuments.models.ProjectEntity;
import com.builder.documents.builderdocuments.models.ProjectsStateEntity;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsStatesRepository extends JpaRepository<ProjectsStateEntity, Long>{
    List<ProjectsStateEntity> findByIdProject(ProjectEntity project);
}
