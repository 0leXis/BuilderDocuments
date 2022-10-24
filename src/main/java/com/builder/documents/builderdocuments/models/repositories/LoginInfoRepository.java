package com.builder.documents.builderdocuments.models.repositories;

import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfoEntity, Long> {
    
}
