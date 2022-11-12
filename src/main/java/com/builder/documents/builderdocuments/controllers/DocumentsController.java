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
    public String documentsGet(ModelMap model, @RequestParam("page") Optional<String> page) {//TODO Recheck the code
      try {
        int currentPage;
        if(page.isPresent())
            currentPage = Integer.parseInt(page.get());
        else
            currentPage = 1;
        Page<DocumentEntity> documentsSet = documents.findAll(PageRequest.of(currentPage - 1, 1));
        model.put("documents", documentsSet);
        model.addAttribute("currentPage", currentPage);
        return "documents";
      }
      catch (NumberFormatException e) {
        return "redirect:/documents";
      }
   }

   @RequestMapping(consumes={"multipart/*"}, method = RequestMethod.POST)
   public String documentsPost(DocumentEntity info, ModelMap model, @RequestParam("secretKey") String secretKey, @RequestParam("document") MultipartFile document) {
        String errorMessage = null;
        String successMessage = null;
        errorMessage = documentsService.addDocument(info, document, secretKey);
        if(errorMessage == null)
        {
          model.addAttribute("success", successMessage);
          return "redirect:/document?item=" + info.getIdDocument();
        }
        else
            model.addAttribute("error", errorMessage);
        return documentsGet(model, Optional.of("1"));
    }
}
