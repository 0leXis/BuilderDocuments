package com.builder.documents.builderdocuments.models.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.builder.documents.builderdocuments.models.ProjectEntity;
import com.builder.documents.builderdocuments.models.interfaces.IProjectService;
import com.builder.documents.builderdocuments.models.repositories.ProjectsRepository;

@Service
public class ProjectService implements IProjectService{

    @Autowired
    ProjectsRepository projectRepo;
    
    @Override
    public String addProject(ProjectEntity project) {
        Optional<ProjectEntity> projectEntity = projectRepo.findById(project.getIdProjects());
        if(projectEntity.isPresent())
            return "Project exists";
        //TODO Transactions
        if(project.getName() == null || project.getName().length() < 1)
            return "Enter name";

        projectRepo.save(project);
        return null;
    }

    @Override
    public String editProject(ProjectEntity project) {
        Optional<ProjectEntity> projectEntity = projectRepo.findById(project.getIdProjects());
        if(!projectEntity.isPresent())
            return "Staff doesn't exists";
        if(project.getName() == null || project.getName().length() < 1)
            return "Enter name";//TODO Validate

        projectRepo.save(project);
        return null;
    }

    @Override
    public String deleteProject(ProjectEntity project) {
        Optional<ProjectEntity> projectEntity = projectRepo.findById(project.getIdProjects());
        if(projectEntity == null){
            return "Project doesn't exists";
        }

        projectRepo.delete(project);//TODO Foreign keys try catch
        return null;
    }

}
