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
import com.builder.documents.builderdocuments.models.StaffEntity;
import com.builder.documents.builderdocuments.models.interfaces.ILoginInfoService;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;
import com.builder.documents.builderdocuments.models.repositories.StaffRepository;

@Controller
@RequestMapping("/staff")
public class StaffController {

   @Autowired
   ILoginInfoService loginInfoService;
   @Autowired
   LoginInfoRepository loginInfos;
   @Autowired
   StaffRepository staff;

   @RequestMapping(method = RequestMethod.GET)
   public String staffGet(ModelMap model, @RequestParam("staffPage") Optional<String> staffPage, @RequestParam("loginPage") Optional<String> loginPage) {
          try {
               int currentPage;
               if(staffPage.isPresent())
                    currentPage = Integer.parseInt(staffPage.get());
               else
                    currentPage = 1;
               Page<StaffEntity> staffSet = staff.findAll(PageRequest.of(currentPage - 1, 1));
               model.put("staff", staffSet);
               model.addAttribute("currentStaffPage", currentPage);

               if(loginPage.isPresent())
                    currentPage = Integer.parseInt(loginPage.get());
               else
                    currentPage = 1;
               Page<LoginInfoEntity> users = loginInfos.findAll(PageRequest.of(currentPage - 1, 1));
               model.put("users", users);
               model.addAttribute("currentLoginPage", currentPage);

               return "staff";
        }
        catch (NumberFormatException e) {
               return "redirect:/staff";
        }
     }

   @RequestMapping(method = RequestMethod.POST)
   public String staffPost(LoginInfoEntity info, ModelMap model, @RequestParam("mode") String mode) {
     return staffGet(model, Optional.of("1"), Optional.of("1"));
    }
}