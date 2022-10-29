package com.builder.documents.builderdocuments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

import org.springframework.beans.factory.annotation.*;

import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import com.builder.documents.builderdocuments.models.interfaces.ILoginInfoService;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;

@Controller
@RequestMapping("/addUser")
public class AddUserController {

   @Autowired
   ILoginInfoService loginInfoService;
   @Autowired
   LoginInfoRepository loginInfos;

   @RequestMapping(method = RequestMethod.GET)
   public String printHello() {
      return "addUser";
   }

   @RequestMapping(method = RequestMethod.POST)
   public String printHello(LoginInfoEntity info, ModelMap model) {
    
        String errorMessage = loginInfoService.saveUser(info);
        if(errorMessage == null)
            model.addAttribute("success", "User created");
        else
            model.addAttribute("error", errorMessage);
        return "addUser";
    }
}