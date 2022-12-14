package com.builder.documents.builderdocuments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.ModelMap;

import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.builder.documents.builderdocuments.config.MvcConfig;
import com.builder.documents.builderdocuments.models.DocumentTemplateEntity;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentTemplateService;
import com.builder.documents.builderdocuments.models.repositories.DocumentTemplateRepository;

@Controller
@RequestMapping("/documentTemplates")
public class TemplatesController {
    @Autowired
    DocumentTemplateRepository templates;
    @Autowired
    IDocumentTemplateService templatesService;

    @RequestMapping(method = RequestMethod.GET)
    public String documentsGet(ModelMap model, @RequestParam("page") Optional<String> page, @RequestParam("sort") Optional<String> sort, @RequestParam("desc") Optional<String> desc) {
      try {
        int currentPage;
        if(page.isPresent())
            currentPage = Integer.parseInt(page.get());
        else
            currentPage = 1;

        Page<DocumentTemplateEntity> templatesSet;

        Direction sortDirection = Sort.Direction.ASC;
        if(desc.isPresent())
            sortDirection = Sort.Direction.DESC;

        if(sort.isPresent())
            templatesSet = templates.findAll(PageRequest.of(currentPage - 1, MvcConfig.PaginationSize, Sort.by(sortDirection, sort.get())));
        else
            templatesSet = templates.findAll(PageRequest.of(currentPage - 1, MvcConfig.PaginationSize));

        model.put("sort", sort.isPresent() ? sort.get() : null);
        model.put("desc", desc.isPresent() ? desc.get() : null);   
        model.put("templates", templatesSet);
        model.addAttribute("currentPage", currentPage);
        return "documentTemplates";
      }
      catch (NumberFormatException e) {
        return "redirect:/documentTemplates";
      }
   }

   @RequestMapping(method = RequestMethod.POST)
   public String templateEditPost(DocumentTemplateEntity data, ModelMap model, @RequestParam("mode") String mode) {
     String errorMessage = null;
     String successMessage = null;
     switch(mode)
     {
      case "delete":
        errorMessage = templatesService.deleteTemplate(data);
        successMessage = "Template deleted";
        break;
     }
     if(errorMessage == null)
         model.addAttribute("success", successMessage);
     else
         model.addAttribute("error", errorMessage);

     return "redirect:/documentTemplates";
    }
}
