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

import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import com.builder.documents.builderdocuments.models.interfaces.ILoginInfoService;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;

@Controller
@RequestMapping("/users")
public class UsersController {

   @Autowired
   ILoginInfoService loginInfoService;
   @Autowired
   LoginInfoRepository loginInfos;

   @RequestMapping(method = RequestMethod.GET)
   public String usersGet(ModelMap model, @RequestParam("page") Optional<String> page) {
      try {
        int currentPage;
        if(page.isPresent())
            currentPage = Integer.parseInt(page.get());
        else
            currentPage = 1;
        Page<LoginInfoEntity> users = loginInfos.findAll(PageRequest.of(currentPage - 1, 1));
        model.put("users", users);
        model.addAttribute("currentPage", currentPage);
        return "users";
      }
      catch (NumberFormatException e) {
        return "redirect:/users";
      }
   }

   @RequestMapping(method = RequestMethod.POST)
   public String usersPost(LoginInfoEntity info, ModelMap model, @RequestParam("mode") String mode) {
        String errorMessage = null;
        String successMessage = null;
        switch(mode)
        {
            case "add":
                errorMessage = loginInfoService.addUser(info);
                successMessage = "User created";
                break;
            case "edit":
                errorMessage = loginInfoService.editUser(info);
                successMessage = "Changes saved";
                break;
            case "delete":
                errorMessage = loginInfoService.deleteUser(info);
                successMessage = "User deleted";
                break;
        }
        if(errorMessage == null)
            model.addAttribute("success", successMessage);
        else
            model.addAttribute("error", errorMessage);

        return usersGet(model, Optional.of("1"));
    }
}