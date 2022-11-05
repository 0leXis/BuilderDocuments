package com.builder.documents.builderdocuments.models.repositories;

import com.builder.documents.builderdocuments.models.StaffEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Long> {
}
