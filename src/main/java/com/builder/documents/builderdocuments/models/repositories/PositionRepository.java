package com.builder.documents.builderdocuments.models.repositories;

import com.builder.documents.builderdocuments.models.PositionEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Long> {
}
