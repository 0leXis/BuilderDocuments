package com.builder.documents.builderdocuments.models.interfaces;

import com.builder.documents.builderdocuments.models.OrderEntity;

public interface IOrdersService {
    public String addOrder(OrderEntity order);
    public String editOrder(OrderEntity order);
    public String deleteOrder(OrderEntity order);
}
