package com.builder.documents.builderdocuments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

import java.util.Optional;

import org.springframework.beans.factory.annotation.*;

import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;

@Controller
@RequestMapping("/index")
public class IndexController {

   @Autowired
   LoginInfoRepository loginInfos;

   @RequestMapping(method = RequestMethod.GET)
   public String printHello(ModelMap model) {
      Optional<LoginInfoEntity> loginInfo = loginInfos.findById(1L);
      if(loginInfo.isPresent())
         model.addAttribute("message", loginInfo.get().getLogin() + " " + loginInfo.get().getPassword());
      else
         model.addAttribute("message", "Hello Spring MVC Framework!");
      return "index";
   }

}