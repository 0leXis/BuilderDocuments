package com.builder.documents.builderdocuments.models.repositories;

import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import com.builder.documents.builderdocuments.models.StaffEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Long> {
    Optional<StaffEntity> findByLoginInfo(LoginInfoEntity idLoginInfo);

    @Query(value = "FROM StaffEntity s WHERE NOT EXISTS (FROM DocumentApproverEntity da WHERE da.document = ?1 AND s = da.staff)")
    List<StaffEntity> findPotentialApprovers(DocumentEntity document);
}
