package com.builder.documents.builderdocuments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.DocumentTemplateEntity;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentTemplateService;
import com.builder.documents.builderdocuments.models.repositories.DocumentTemplateRepository;

@Controller
@RequestMapping("/templateEdit")
public class TemplateEditController {
    @Autowired
    DocumentTemplateRepository templates;
    @Autowired
    IDocumentTemplateService templatesService;

    @RequestMapping(method = RequestMethod.GET)
    public String templateEditGet(ModelMap model, @RequestParam("item") String item) {
        try {
            Optional<DocumentTemplateEntity> template;
            String templateText;
            if(item.equals("create"))
            {
                template = Optional.of(new DocumentTemplateEntity());
                templateText = "";
            }
            else
            {
                long intItem;
                intItem = Long.parseLong(item);
        
                template = templates.findById(intItem);

                if(!template.isPresent())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");

                FileSystemResource resource = new FileSystemResource(template.get().getPath()); 
                templateText = new String(Files.readAllBytes(Paths.get(resource.getPath())), StandardCharsets.UTF_8);
            }

            model.put("template", template.get());
            model.put("templateText", templateText);
            return "templateEdit";
          }
          catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
          }
          catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
          }
   }

   @RequestMapping(method = RequestMethod.POST)
   public String templateEditPost(DocumentTemplateEntity data, ModelMap model, @RequestParam("mode") String mode, @RequestParam("templateText") String templateText) {
     String errorMessage = null;
     String successMessage = null;
     switch(mode)
     {
      case "add":
        errorMessage = templatesService.addTemplate(data, templateText);
        successMessage = "Template added";
        break;
      case "edit":
        errorMessage = templatesService.editTemplate(data, templateText);
        successMessage = "Template edited";
        break;
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
