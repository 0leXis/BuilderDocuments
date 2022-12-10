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
import org.springframework.http.HttpStatus;

import com.builder.documents.builderdocuments.models.DocumentApproverEntity;
import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.StaffEntity;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentService;
import com.builder.documents.builderdocuments.models.repositories.DocumentApproversRepository;
import com.builder.documents.builderdocuments.models.repositories.DocumentsRepository;
import com.builder.documents.builderdocuments.models.repositories.StaffRepository;

@Controller
@RequestMapping("/documentEdit")
public class DocumentEditController {
    @Autowired
    DocumentsRepository documents;
    @Autowired
    StaffRepository staffRepo;
    @Autowired
    DocumentApproversRepository approversRepo;
    @Autowired
    IDocumentService documentService;

    @RequestMapping(method = RequestMethod.GET)
    public String documentEditGet(ModelMap model, @RequestParam("item") String item) {
      try {
        long intItem;
        intItem = Long.parseLong(item);

        Optional<DocumentEntity> document = documents.findById(intItem);

        if(!document.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");

        List<DocumentApproverEntity> approvers = approversRepo.findByDocument(document.get());

        List<StaffEntity> staff = staffRepo.findAll();
        List<StaffEntity> approversStaff = staffRepo.findPotentialApprovers(document.get(), document.get().getCreator());

        model.put("document", document.get());
        model.put("approvers", approvers);
        model.put("approversStaff", approversStaff);
        model.put("staff", staff);
        return "documentEdit";
      }
      catch (NumberFormatException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
      }
   }

   @RequestMapping(method = RequestMethod.POST)
   public String documentEditPost(ModelMap model, DocumentEntity data, @RequestParam("mode") String mode, @RequestParam("item") String item, @RequestParam("approverId") Optional<String> approverId, @RequestParam("assigneeId") Optional<String> assigneeId) {
     String errorMessage = null;
     String successMessage = null;
     switch(mode)
     {
         case "addApprover":
             if(!approverId.isPresent())
             {
                errorMessage = "No approver id provided";
                break;
             }
             errorMessage = documentService.addApprover(approverId.get(), item);//TODO Message output
             successMessage = "Approver added";
             break;
         case "deleteApprover":
            errorMessage = documentService.deleteApprover(approverId.get());//TODO Message output
            successMessage = "Approver deleted";
            break;
         case "changeAssignee":
             if(!assigneeId.isPresent())
             {
                errorMessage = "No assignee id provided";
                break;
             }
             errorMessage = documentService.changeAssignee(assigneeId.get(), item);//TODO Message output
             successMessage = "Assignee changed";
             break;
          case "editDocument":
            errorMessage = documentService.editDocument(data);//TODO Message output
            successMessage = "Document changed";
            break;
     }
     if(errorMessage == null)
         model.addAttribute("success", successMessage);
     else
         model.addAttribute("error", errorMessage);

     if(mode.equals("editDocument"))
        return "redirect:/document?item=" + item;
     return documentEditGet(model, item);
    }
}
