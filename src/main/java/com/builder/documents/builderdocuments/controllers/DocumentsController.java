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

import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import com.builder.documents.builderdocuments.models.interfaces.ILoginInfoService;
import com.builder.documents.builderdocuments.models.repositories.DocumentsRepository;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;

@Controller
@RequestMapping("/documents")
public class DocumentsController {
    @Autowired
    DocumentsRepository documents;

    @RequestMapping(method = RequestMethod.GET)
    public String usersGet(ModelMap model, @RequestParam("page") Optional<String> page) {
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
}
