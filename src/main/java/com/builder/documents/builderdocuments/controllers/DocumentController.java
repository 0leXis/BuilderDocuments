package com.builder.documents.builderdocuments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.ModelMap;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.builder.documents.builderdocuments.models.DocumentApproverEntity;
import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.StaffEntity;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentService;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentsService;
import com.builder.documents.builderdocuments.models.interfaces.IStaffService;
import com.builder.documents.builderdocuments.models.repositories.DocumentApproversRepository;
import com.builder.documents.builderdocuments.models.repositories.DocumentsRepository;
import com.builder.documents.builderdocuments.models.repositories.StaffRepository;

@Controller
public class DocumentController {
    @Autowired
    DocumentsRepository documents;
    @Autowired
    StaffRepository staffRepo;
    @Autowired
    DocumentApproversRepository approversRepo;
    @Autowired
    IDocumentService documentService;
    @Autowired
    IDocumentsService documentsService;
    @Autowired
    IStaffService staffService;
    @Autowired
    ResourceLoader resourceLoader;

    @RequestMapping(value = "/document", method = RequestMethod.GET)
    public String documentGet(ModelMap model, @RequestParam("item") String item) {
      try {
        long intItem;
        intItem = Long.parseLong(item);

        Optional<DocumentEntity> document = documents.findById(intItem);

        if(!document.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");

        List<DocumentApproverEntity> approvers = approversRepo.findByDocument(document.get());

        DocumentApproverEntity currentApprover = null;
        StaffEntity currentStaff = staffService.getCurrentStaff().get();
        for (DocumentApproverEntity documentApproverEntity : approvers) {
          if(documentApproverEntity.getStaff() == currentStaff){
            currentApprover = documentApproverEntity;
            break;
          }
        }

        model.put("document", document.get());
        model.put("approvers", approvers);
        model.put("currentApprover", currentApprover);
        return "document";
      }
      catch (NumberFormatException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
      }
   }

   @RequestMapping(value = "/document/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
   @ResponseBody
   public FileSystemResource getFile(@RequestParam("item") String item) {
    try {
      long intItem;
      intItem = Long.parseLong(item);

      Optional<DocumentEntity> document = documents.findById(intItem);

      if(!document.isPresent())
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");

      FileSystemResource resource = new FileSystemResource(document.get().getPath()); 
      return resource; 
    }
    catch (NumberFormatException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
    } 
  }

   @RequestMapping(value = "/document", method = RequestMethod.POST)
   public String documentPost(DocumentEntity data, ModelMap model, @RequestParam("mode") String mode) {
     String errorMessage = null;
     String successMessage = null;
     switch(mode)
     {
      case "delete":
        errorMessage = documentsService.deleteDocument(data);
        successMessage = "Document deleted";
        break;
     }
     if(errorMessage == null)
         model.addAttribute("success", successMessage);
     else
         model.addAttribute("error", errorMessage);

     return "redirect:/documents";
    }

    @RequestMapping(value = "/documentApprove", method = RequestMethod.POST)
    public String documentApprovePost(DocumentEntity data, ModelMap model) {
      String errorMessage = null;
      String successMessage = "Approved";

      errorMessage = documentService.approveDocument(data);

      if(errorMessage == null)
          model.addAttribute("success", successMessage);
      else
          model.addAttribute("error", errorMessage);
 
      return "redirect:/document?item=" + data.getIdDocument();
     }
}
