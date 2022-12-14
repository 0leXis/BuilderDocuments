package com.builder.documents.builderdocuments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.builder.documents.builderdocuments.config.MvcConfig;
import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import com.builder.documents.builderdocuments.models.PositionEntity;
import com.builder.documents.builderdocuments.models.StaffEntity;
import com.builder.documents.builderdocuments.models.interfaces.IStaffService;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;
import com.builder.documents.builderdocuments.models.repositories.PositionRepository;
import com.builder.documents.builderdocuments.models.repositories.StaffRepository;

@Controller
@RequestMapping("/staff")
public class StaffController {

   @Autowired
   IStaffService staffService;
   @Autowired
   LoginInfoRepository loginInfos;
   @Autowired
   StaffRepository staff;
   @Autowired
   PositionRepository positions;

   @RequestMapping(method = RequestMethod.GET)
   public String staffGet(ModelMap model, @RequestParam("staffPage") Optional<String> staffPage, @RequestParam("loginPage") Optional<String> loginPage, @RequestParam("sort") Optional<String> sort, @RequestParam("desc") Optional<String> desc) {
          try {
               int currentPage;

               boolean sortUser = sort.isPresent() && 
                    (sort.get().equals("idLoginInfo") ||
                     sort.get().equals("login")) ? true : false;
               Direction sortDirection = Sort.Direction.ASC;
               if(desc.isPresent())
                    sortDirection = Sort.Direction.DESC;

               if(staffPage.isPresent())
                    currentPage = Integer.parseInt(staffPage.get());
               else
                    currentPage = 1;
               Page<StaffEntity> staffSet;
               if(sort.isPresent() && sortUser == false)
                    staffSet = staff.findAll(PageRequest.of(currentPage - 1, MvcConfig.PaginationSize, Sort.by(sortDirection, sort.get())));
                else
                    staffSet = staff.findAll(PageRequest.of(currentPage - 1, MvcConfig.PaginationSize));
               model.put("staff", staffSet);
               model.addAttribute("currentStaffPage", currentPage);

               if(loginPage.isPresent())
                    currentPage = Integer.parseInt(loginPage.get());
               else
                    currentPage = 1;
               Page<LoginInfoEntity> users;
               if(sort.isPresent() && sortUser == true)
                    users = loginInfos.findNotActiveUsers(PageRequest.of(currentPage - 1, MvcConfig.PaginationSize, Sort.by(sortDirection, sort.get())));
               else
                    users = loginInfos.findNotActiveUsers(PageRequest.of(currentPage - 1, MvcConfig.PaginationSize));
               model.put("users", users);
               model.addAttribute("currentLoginPage", currentPage);

               List<PositionEntity> positionsSet = positions.findAll();
               model.put("positions", positionsSet);
       
               model.put("sort", sort.isPresent() ? sort.get() : null);
               model.put("desc", desc.isPresent() ? desc.get() : null); 
               
               return "staff";
        }
        catch (NumberFormatException e) {
               return "redirect:/staff";
        }
     }

   @RequestMapping(method = RequestMethod.POST)
   public String staffPost(StaffEntity data, ModelMap model, @RequestParam("mode") String mode) {
     String errorMessage = null;
     String successMessage = null;
     switch(mode)
     {
         case "add":
             errorMessage = staffService.addStaff(data);//TODO Message output
             successMessage = "Staff created";
             break;
         case "edit":
             errorMessage = staffService.editStaff(data);
             successMessage = "Changes saved";
             break;
         case "delete":
             errorMessage = staffService.deleteStaff(data);
             successMessage = "Staff deleted";
             break;
     }
     if(errorMessage == null)
         model.addAttribute("success", successMessage);
     else
         model.addAttribute("error", errorMessage);

     return staffGet(model, Optional.of("1"), Optional.of("1"), Optional.empty(), Optional.empty());
    }
}