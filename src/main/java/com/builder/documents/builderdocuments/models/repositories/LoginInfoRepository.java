package com.builder.documents.builderdocuments.models.repositories;

import com.builder.documents.builderdocuments.models.LoginInfoEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfoEntity, Long> {
    LoginInfoEntity findByLogin(String login);

    @Query(value = "FROM LoginInfoEntity li WHERE NOT EXISTS (FROM StaffEntity s WHERE s.loginInfo = li.idLoginInfo)")
    Page<LoginInfoEntity> findNotActiveUsers(Pageable page);
}
