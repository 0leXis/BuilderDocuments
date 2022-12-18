package com.builder.documents.builderdocuments.models.repositories;

import com.builder.documents.builderdocuments.models.OrderEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, Long> {
    
}
