package com.builder.documents.builderdocuments.models.interfaces;

import java.security.KeyPair;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.builder.documents.builderdocuments.models.StaffEntity;

public interface IStaffService {
    public Optional<StaffEntity> getCurrentStaff();

    public String addStaff(StaffEntity staff);
    public String editStaff(StaffEntity staff);
    public String deleteStaff(StaffEntity staff);

    public String generateKeyValuePair(StaffEntity staff, AtomicReference<String> privateKey);
}
