package com.builder.documents.builderdocuments.models.services;

import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
    public Optional<StaffEntity> getCurrentStaff(){
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<StaffEntity> currentStaff = staffRepo.findByLoginInfo(loginRepo.findByLogin(currentUser.getUsername()));
        
        return currentStaff;
    }

    @Override
    public String addStaff(StaffEntity staff) {
        Optional<StaffEntity> staffEntity = staffRepo.findByLoginInfo(staff.getLoginInfo());
        if(staffEntity.isPresent())
            return "Staff exists";

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

        staffRepo.delete(staff);
        return null;
    }
    
    @Override
    public String generateKeyValuePair(StaffEntity staff, AtomicReference<String> privateKey)
    {
        if(staff.getOpenKey() != null)
            return "User already generated the key";

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            staff.setOpenKey(
                new String(
                    Base64.getEncoder().encode(
                        pair.getPublic().getEncoded()
                        )));
            privateKey.set(new String(
                Base64.getEncoder().encode(
                    pair.getPrivate().getEncoded()
                    )));
            staffRepo.save(staff);
        } catch (NoSuchAlgorithmException e) {
            return "No RSA generator found";
        }

        return null;
    }
}
