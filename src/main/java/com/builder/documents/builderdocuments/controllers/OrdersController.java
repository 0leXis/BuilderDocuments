package com.builder.documents.builderdocuments.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.builder.documents.builderdocuments.config.MvcConfig;
import com.builder.documents.builderdocuments.models.ClientEntity;
import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.OrderEntity;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentsService;
import com.builder.documents.builderdocuments.models.interfaces.IOrdersService;
import com.builder.documents.builderdocuments.models.repositories.ClientsRepository;
import com.builder.documents.builderdocuments.models.repositories.DocumentsRepository;
import com.builder.documents.builderdocuments.models.repositories.OrdersRepository;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    IOrdersService ordersService;

    @Autowired
    OrdersRepository ordersRepo;
    @Autowired
    ClientsRepository clientsRepo;

    @RequestMapping(method = RequestMethod.GET)
    public String ordersGet(ModelMap model, @RequestParam("page") Optional<String> page, @RequestParam("sort") Optional<String> sort, @RequestParam("desc") Optional<String> desc) {
        try {
            int currentPage;
            if(page.isPresent())
                currentPage = Integer.parseInt(page.get());
            else
                currentPage = 1;
    
            Page<OrderEntity> orders;
    
            Direction sortDirection = Sort.Direction.ASC;
            if(desc.isPresent())
                sortDirection = Sort.Direction.DESC;
    
            if(sort.isPresent())
                orders = ordersRepo.findAll(PageRequest.of(currentPage - 1, MvcConfig.PaginationSize, Sort.by(sortDirection, sort.get())));
            else
                orders = ordersRepo.findAll(PageRequest.of(currentPage - 1, MvcConfig.PaginationSize));
    
            List<ClientEntity> clients = clientsRepo.findAll();

            model.put("sort", sort.isPresent() ? sort.get() : null);
            model.put("desc", desc.isPresent() ? desc.get() : null);   
            model.put("clients", clients);
            model.put("orders", orders);
            model.addAttribute("currentPage", currentPage);
            return "orders";
          }
          catch (NumberFormatException e) {
            return "redirect:/orders";
          }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String ordersPost(OrderEntity info, ModelMap model, @RequestParam("mode") String mode) {
         String errorMessage = null;
         String successMessage = null;
         switch(mode)
         {
             case "add":
                 errorMessage = ordersService.addOrder(info);
                 successMessage = "Order created";
                 break;
             case "edit":
                 errorMessage = ordersService.editOrder(info);
                 successMessage = "Changes saved";
                 break;
             case "delete":
                 errorMessage = ordersService.deleteOrder(info);
                 successMessage = "Order deleted";
                 break;
         }
         if(errorMessage == null)
             model.addAttribute("success", successMessage);
         else
             model.addAttribute("error", errorMessage);
 
         return ordersGet(model, Optional.of("1"), Optional.empty(), Optional.empty());
     }
}
