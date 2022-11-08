package com.builder.documents.builderdocuments.models.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.builder.documents.builderdocuments.models.StaffEntity;
import com.builder.documents.builderdocuments.models.interfaces.IStaffService;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;
import com.builder.documents.builderdocuments.models.repositories.StaffRepository;

@Service
public class StaffService implements IStaffService {

    @Autowired
    StaffRepository staffRepo;
    @Autowired
    LoginInfoRepository loginRepo;

    @Override
    public String addStaff(StaffEntity staff) {
        Optional<StaffEntity> staffEntity = staffRepo.findByLoginInfo(staff.getLoginInfo());
        if(staffEntity.isPresent())
            return "Staff exists";
        //TODO Transactions
        if(staff.getName().length() < 1 || staff.getLastname().length() < 1)
            return "Enter name and lastname";
        if(staff.getSalary().compareTo(BigDecimal.valueOf(0)) < 0)
            return "Salary must be positive";
        if(staff.getPosition() == null)
            return "Enter valid position";

        staffRepo.save(staff);
        return null;
    }

    @Override
    public String editStaff(StaffEntity staff) {
        Optional<StaffEntity> staffEntity = staffRepo.findById(staff.getIdStaff());
        if(!staffEntity.isPresent())
            return "Staff doesn't exists";
        if(staff.getName().length() < 1 || staff.getLastname().length() < 1)
            return "Enter name and lastname";
        if(staff.getSalary().compareTo(BigDecimal.valueOf(0)) < 0)
            return "Salary must be positive";
        if(staff.getPosition() == null)
            return "Enter valid position";

        staff.setLoginInfo(staffEntity.get().getLoginInfo());
        staffRepo.save(staff);
        return null;
    }

    @Override
    public String deleteStaff(StaffEntity staff) {
        Optional<StaffEntity> staffEntity = staffRepo.findById(staff.getIdStaff());
        if(staffEntity == null){
            return "Staff doesn't exists";
        }

        staffRepo.delete(staff);//TODO Foreign keys try catch
        return null;
    }
    
}
