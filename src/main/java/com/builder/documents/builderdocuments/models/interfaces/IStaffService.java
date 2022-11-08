package com.builder.documents.builderdocuments.models.interfaces;

import com.builder.documents.builderdocuments.models.StaffEntity;

public interface IStaffService {
    public String addStaff(StaffEntity staff);
    public String editStaff(StaffEntity staff);
    public String deleteStaff(StaffEntity staff);
}
