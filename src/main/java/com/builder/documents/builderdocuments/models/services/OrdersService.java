package com.builder.documents.builderdocuments.models.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.builder.documents.builderdocuments.models.OrderEntity;
import com.builder.documents.builderdocuments.models.interfaces.IOrdersService;
import com.builder.documents.builderdocuments.models.repositories.OrdersRepository;

@Service
public class OrdersService implements IOrdersService{

    @Autowired
    OrdersRepository ordersRepo;
    
    @Override
    public String addOrder(OrderEntity order) {
        Optional<OrderEntity> orderExists = ordersRepo.findById(order.getIdOrders());
        if(orderExists.isPresent()){
            return "Order already exists";
        }
        if(order.getName() == null || order.getName().length() < 1){
            return "Enter name";
        }
        if(order.getClient() == null){
            return "Client should be set";
        }
        if(order.getDescription() == null)
            order.setDescription("");

        ordersRepo.save(order);
        return null;
    }

    @Override
    public String editOrder(OrderEntity order) {
        Optional<OrderEntity> orderExists = ordersRepo.findById(order.getIdOrders());
        if(!orderExists.isPresent()){
            return "Order doesn't exists";
        }
        if(order.getName() == null || order.getName().length() < 1){
            return "Enter name";
        }
        if(order.getClient() == null){
            return "Client should be set";
        }
        if(order.getDescription() == null)
            order.setDescription("");

        ordersRepo.save(order);
        return null;
    }

    @Override
    public String deleteOrder(OrderEntity order) {
        Optional<OrderEntity> orderExists = ordersRepo.findById(order.getIdOrders());
        if(!orderExists.isPresent()){
            return "Order doesn't exists";
        }

        ordersRepo.delete(order);
        return null;
    }
    
}
