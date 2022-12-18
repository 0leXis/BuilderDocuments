package com.builder.documents.builderdocuments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;

import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.builder.documents.builderdocuments.config.MvcConfig;
import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import com.builder.documents.builderdocuments.models.ProjectEntity;
import com.builder.documents.builderdocuments.models.interfaces.ILoginInfoService;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;
import com.builder.documents.builderdocuments.models.repositories.ProjectsRepository;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    ProjectsRepository projectsRepo;

    @RequestMapping(method = RequestMethod.GET)
    public String projectsGet(ModelMap model, @RequestParam("page") Optional<String> page, @RequestParam("sort") Optional<String> sort, @RequestParam("desc") Optional<String> desc) {
       try {
         int currentPage;
         if(page.isPresent())
             currentPage = Integer.parseInt(page.get());
         else
             currentPage = 1;
 
         Page<ProjectEntity> projects;
 
         Direction sortDirection = Sort.Direction.ASC;
         if(desc.isPresent())
             sortDirection = Sort.Direction.DESC;
 
         if(sort.isPresent())
            projects = projectsRepo.findAll(PageRequest.of(currentPage - 1, MvcConfig.PaginationSize, Sort.by(sortDirection, sort.get())));
         else
            projects = projectsRepo.findAll(PageRequest.of(currentPage - 1, MvcConfig.PaginationSize));
 
         model.put("sort", sort.isPresent() ? sort.get() : null);
         model.put("desc", desc.isPresent() ? desc.get() : null);   
         model.put("projects", projects);
         model.addAttribute("currentPage", currentPage);
         return "projects";
       }
       catch (NumberFormatException e) {
         return "redirect:/projects";
       }
    }
}
