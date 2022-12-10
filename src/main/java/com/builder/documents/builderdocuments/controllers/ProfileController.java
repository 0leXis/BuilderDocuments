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

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import com.builder.documents.builderdocuments.models.PositionEntity;
import com.builder.documents.builderdocuments.models.StaffEntity;
import com.builder.documents.builderdocuments.models.interfaces.IStaffService;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;
import com.builder.documents.builderdocuments.models.repositories.PositionRepository;
import com.builder.documents.builderdocuments.models.repositories.StaffRepository;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    IStaffService staffService;

    @RequestMapping(method = RequestMethod.GET)
    public String profileGet(ModelMap model) {
        Optional<StaffEntity> currentUser = staffService.getCurrentStaff();
        if(!currentUser.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find user");

        model.put("staff", currentUser.get());
        return "profile";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String profilePost(ModelMap model) {
        Optional<StaffEntity> currentUser = staffService.getCurrentStaff();
        if(!currentUser.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find user");

        AtomicReference<String> privateKey = new AtomicReference<String>();
        String errorMessage = staffService.generateKeyValuePair(currentUser.get(), privateKey);
        if(errorMessage == null)
            model.addAttribute("privateKey", privateKey.get());
        else
            model.addAttribute("error", errorMessage);

        return profileGet(model);
    }
}
