package com.builder.documents.builderdocuments.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.builder.documents.builderdocuments.models.ClientEntity;

@Repository
public interface ClientsRepository extends JpaRepository<ClientEntity, Long>{
    
}
