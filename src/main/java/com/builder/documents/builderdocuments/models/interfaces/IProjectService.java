package com.builder.documents.builderdocuments.models.interfaces;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.builder.documents.builderdocuments.models.ProjectEntity;

public interface IProjectService {
    public String addProject(ProjectEntity project);
    public String editProject(ProjectEntity project);
    public String deleteProject(ProjectEntity project);
    public String createDocument(ProjectEntity project, String secretKey);
}
