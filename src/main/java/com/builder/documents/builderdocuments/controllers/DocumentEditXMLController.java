package com.builder.documents.builderdocuments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;

import com.builder.documents.builderdocuments.models.DocumentApproverEntity;
import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.DocumentTemplateEntity;
import com.builder.documents.builderdocuments.models.StaffEntity;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentService;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentsService;
import com.builder.documents.builderdocuments.models.repositories.DocumentApproversRepository;
import com.builder.documents.builderdocuments.models.repositories.DocumentTemplateRepository;
import com.builder.documents.builderdocuments.models.repositories.DocumentsRepository;
import com.builder.documents.builderdocuments.models.repositories.StaffRepository;

@Controller
@RequestMapping("/documentEditXML")
public class DocumentEditXMLController {
    @Autowired
    DocumentsRepository documents;
    @Autowired
    StaffRepository staffRepo;
    @Autowired
    DocumentApproversRepository approversRepo;
    @Autowired
    DocumentTemplateRepository templatesRepo;
    @Autowired
    IDocumentsService documentsService;

    @RequestMapping(method = RequestMethod.GET)
    public String documentEditGet(ModelMap model, @RequestParam("item") String item) {
      try {
        Optional<DocumentEntity> document;
        String documentText;
        if(item.equals("create"))
        {
            document = Optional.of(new DocumentEntity());
            documentText = "";
        }
        else
        {
            long intItem;
            intItem = Long.parseLong(item);
    
            document = documents.findById(intItem);
    
            if(!document.isPresent())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
    
            if(!document.get().isFormalized())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to change non formalized documents");

            FileSystemResource resource = new FileSystemResource(document.get().getPath()); 
            documentText = new String(Files.readAllBytes(Paths.get(resource.getPath())), StandardCharsets.UTF_8);
        }

        List<DocumentTemplateEntity> templates = templatesRepo.findAll();

        model.put("document", document.get());
        model.put("templates", templates);
        model.put("documentText", documentText);
        return "documentEditXML";
      }
      catch (NumberFormatException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
      }
      catch (IOException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
      }
   }

   @RequestMapping(method = RequestMethod.POST)
   public String documentsPost(DocumentEntity info, ModelMap model, @RequestParam("mode") String mode, @RequestParam("secretKey") String secretKey, @RequestParam("documentText") String documentText) {
        String errorMessage = null;
        String successMessage = null;
        switch(mode){
            case "upload":
              errorMessage = documentsService.addDocument(info, documentText, secretKey);
              break;
            case "uploadNew":
              errorMessage = documentsService.editDocument(info, documentText, secretKey);
              break;
          }
        if(errorMessage == null)
        {
          model.addAttribute("success", successMessage);
          return "redirect:/document?item=" + info.getIdDocument();
        }
        else
            model.addAttribute("error", errorMessage);
        return "redirect:/documents";// TODO check redirects and error placement
    }
}
