package com.builder.documents.builderdocuments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;

import com.builder.documents.builderdocuments.config.MvcConfig;
import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import com.builder.documents.builderdocuments.models.PositionEntity;
import com.builder.documents.builderdocuments.models.ProjectEntity;
import com.builder.documents.builderdocuments.models.ProjectMaterialEntity;
import com.builder.documents.builderdocuments.models.ProjectsStateEntity;
import com.builder.documents.builderdocuments.models.StaffEntity;
import com.builder.documents.builderdocuments.models.interfaces.IStaffService;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;
import com.builder.documents.builderdocuments.models.repositories.PositionRepository;
import com.builder.documents.builderdocuments.models.repositories.ProjectMaterialRepository;
import com.builder.documents.builderdocuments.models.repositories.ProjectsRepository;
import com.builder.documents.builderdocuments.models.repositories.ProjectsStatesRepository;
import com.builder.documents.builderdocuments.models.repositories.StaffRepository;
import com.builder.documents.builderdocuments.models.services.ProjectService;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectsRepository projectsRepo;
    @Autowired
    ProjectsStatesRepository projectsStatesRepo;
    @Autowired
    ProjectMaterialRepository projectMaterialsRepo;
    @Autowired
    ProjectService projectService;

    @RequestMapping(method = RequestMethod.GET)
    public String projectGet(ModelMap model, @RequestParam("item") String item) {
      try {
        long intItem;
        intItem = Long.parseLong(item);

        Optional<ProjectEntity> project = projectsRepo.findById(intItem);

        if(!project.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");

        List<ProjectsStateEntity> projectStates = projectsStatesRepo.findByIdProject(project.get());
        List<ProjectMaterialEntity> projectMaterials = projectMaterialsRepo.findByIdProject(project.get());

        model.put("project", project.get());
        model.put("projectStates", projectStates);
        model.put("projectMaterials", projectMaterials);
        return "project";
      }
      catch (NumberFormatException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
      }
   }
    
    @RequestMapping(method = RequestMethod.POST)
    public String staffPost(ProjectEntity data, ModelMap model, @RequestParam("mode") String mode, @RequestParam("secretKey") Optional<String> secretKey) {
      String errorMessage = null;
      String successMessage = null;
      switch(mode)
      {
          case "add":
              errorMessage = projectService.addProject(data);//TODO Message output
              successMessage = "Project created";
              break;
          case "createDocumentFrom":
              if(!secretKey.isPresent())
                errorMessage = "No key provided";
              else{
                errorMessage = projectService.createDocument(data, secretKey.get());
                successMessage = "Document created";
                if(errorMessage.startsWith("redirect"))
                  return errorMessage;
              }
      }
      if(errorMessage == null)
          model.addAttribute("success", successMessage);
      else
          model.addAttribute("error", errorMessage);
 
      return projectGet(model, String.valueOf(data.getIdProjects()));
     }
}
