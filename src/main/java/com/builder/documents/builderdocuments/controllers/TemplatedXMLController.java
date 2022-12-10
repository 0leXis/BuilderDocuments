package com.builder.documents.builderdocuments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import com.builder.documents.builderdocuments.models.DocumentTemplateEntity;
import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import com.builder.documents.builderdocuments.models.PositionEntity;
import com.builder.documents.builderdocuments.models.StaffEntity;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentTemplateService;
import com.builder.documents.builderdocuments.models.interfaces.IStaffService;
import com.builder.documents.builderdocuments.models.interfaces.IXMLValidationService;
import com.builder.documents.builderdocuments.models.repositories.DocumentTemplateRepository;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;
import com.builder.documents.builderdocuments.models.repositories.PositionRepository;
import com.builder.documents.builderdocuments.models.repositories.StaffRepository;

@Controller
@RequestMapping("/templatedXML")
public class TemplatedXMLController {

   @Autowired
   DocumentTemplateRepository templateRepo;
   @Autowired
   IDocumentTemplateService templateService;

   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   public String templatedXMLGet(ModelMap model, @RequestParam("templateId") long templateId) {
        Optional<DocumentTemplateEntity> template = templateRepo.findById(templateId);
        if(!template.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");

        AtomicReference<String> result = new AtomicReference<String>();
        String error = templateService.generateTemplatedDocument(template.get(), result);

        if(error != null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error);

        return result.get();
   }
}
