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
import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentsService;
import com.builder.documents.builderdocuments.models.repositories.DocumentsRepository;

@Controller
@RequestMapping("/documents")
public class DocumentsController {
    @Autowired
    DocumentsRepository documents;
    @Autowired
    IDocumentsService documentsService;

    @RequestMapping(method = RequestMethod.GET)
    public String documentsGet(ModelMap model, @RequestParam("page") Optional<String> page, @RequestParam("sort") Optional<String> sort, @RequestParam("desc") Optional<String> desc) {
      try {
        int currentPage;
        if(page.isPresent())
            currentPage = Integer.parseInt(page.get());
        else
            currentPage = 1;

        Page<DocumentEntity> documentsSet;

        Direction sortDirection = Sort.Direction.ASC;
        if(desc.isPresent())
            sortDirection = Sort.Direction.DESC;

        if(sort.isPresent())
            documentsSet = documents.findAll(PageRequest.of(currentPage - 1, MvcConfig.PaginationSize, Sort.by(sortDirection, sort.get())));
        else
            documentsSet = documents.findAll(PageRequest.of(currentPage - 1, MvcConfig.PaginationSize));

        model.put("sort", sort.isPresent() ? sort.get() : null);
        model.put("desc", desc.isPresent() ? desc.get() : null);   
        model.put("documents", documentsSet);
        model.addAttribute("currentPage", currentPage);
        return "documents";
      }
      catch (NumberFormatException e) {
        return "redirect:/documents";
      }
   }

   @RequestMapping(consumes={"multipart/*"}, method = RequestMethod.POST)
   public String documentsPost(DocumentEntity info, ModelMap model, @RequestParam("mode") String mode, @RequestParam("secretKey") String secretKey, @RequestParam("document") MultipartFile document) {
        String errorMessage = null;
        String successMessage = "Document uploaded";
        switch(mode){
          case "upload":
            errorMessage = documentsService.addDocument(info, document, secretKey);
            break;
          case "uploadNew":
            errorMessage = documentsService.editDocument(info, document, secretKey);
            break;
        }
        if(errorMessage == null)
        {
          model.addAttribute("success", successMessage);
          return "redirect:/document?item=" + info.getIdDocument();
        }
        else
            model.addAttribute("error", errorMessage);
        return documentsGet(model, Optional.of("1"), Optional.empty(), Optional.empty());
    }
}
