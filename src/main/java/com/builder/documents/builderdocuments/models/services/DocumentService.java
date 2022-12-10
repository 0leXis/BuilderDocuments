package com.builder.documents.builderdocuments.models.services;

import java.util.Optional;
import java.util.regex.Pattern;

import javax.persistence.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.*;

import com.builder.documents.builderdocuments.models.DocumentApproverEntity;
import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import com.builder.documents.builderdocuments.models.Role;
import com.builder.documents.builderdocuments.models.StaffEntity;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentService;
import com.builder.documents.builderdocuments.models.interfaces.ILoginInfoService;
import com.builder.documents.builderdocuments.models.interfaces.IStaffService;
import com.builder.documents.builderdocuments.models.repositories.DocumentApproversRepository;
import com.builder.documents.builderdocuments.models.repositories.DocumentsRepository;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;
import com.builder.documents.builderdocuments.models.repositories.StaffRepository;

@Service
public class DocumentService implements IDocumentService {
    
    @Autowired
    StaffRepository staffRepo;
    @Autowired
    DocumentsRepository documentsRepo;
    @Autowired
    DocumentApproversRepository approversRepo;
    @Autowired
    IStaffService staffService;

    @Override
    public String addApprover(String approverIdStr, String documentIdStr) {
        long approverId;
        long documentId;
        try{
            approverId = Long.parseLong(approverIdStr);
            documentId = Long.parseLong(documentIdStr);
        }
        catch(Exception e)
        {
            return "Wrong id";
        }

        Optional<StaffEntity> staff = staffRepo.findById(approverId);
        if(!staff.isPresent())
            return "Approver not found";

        Optional<DocumentEntity> document = documentsRepo.findById(documentId);
        if(!document.isPresent())
            return "Document not found";

        if(staff.get() == document.get().getCreator())
            return "Creator can't approve the document";

        Optional<DocumentApproverEntity> approver = approversRepo.findByDocumentAndStaff(document.get(), staff.get());
        if(approver.isPresent())
            return "Approver already added";

        DocumentApproverEntity newApprover = new DocumentApproverEntity();
        newApprover.setIdDocumentApprover(null);
        newApprover.setApproved(false);
        newApprover.setDocument(document.get());
        newApprover.setStaff(staff.get());
        
        approversRepo.save(newApprover);
        return null;
    }

    @Override
    public String deleteApprover(String approverIdStr){
        long approverId;
        try{
            approverId = Long.parseLong(approverIdStr);
        }
        catch(Exception e)
        {
            return "Wrong id";
        }

        Optional<DocumentApproverEntity> approver = approversRepo.findById(approverId);
        if(!approver.isPresent())
            return "Approver not found";

        approversRepo.delete(approver.get());

        return null;
    }

    @Override
    public String changeAssignee(String assigneeIdStr, String documentIdStr) {
        long assigneeId;
        long documentId;
        try{
            assigneeId = Long.parseLong(assigneeIdStr);
            documentId = Long.parseLong(documentIdStr);
        }
        catch(Exception e)
        {
            return "Wrong id";
        }

        Optional<StaffEntity> staff = staffRepo.findById(assigneeId);
        if(!staff.isPresent())
            return "Assignee not found";

        Optional<DocumentEntity> document = documentsRepo.findById(documentId);
        if(!document.isPresent())
            return "Document not found";

        document.get().setAssignee(staff.get());
        documentsRepo.save(document.get());

        return null;
    }

    @Override
    public String editDocument(DocumentEntity document) {
        Optional<DocumentEntity> documentEntity = documentsRepo.findById(document.getIdDocument());
        if(!documentEntity.isPresent())
            return "Document doesn't exists";

        documentEntity.get().setName(document.getName());
        documentEntity.get().setDescription(document.getDescription());
        documentsRepo.save(documentEntity.get());

        return null;
    }

    @Override
    public String approveDocument(DocumentEntity info)
    {
        Optional<DocumentEntity> documentEntity = documentsRepo.findById(info.getIdDocument());
        if(documentEntity == null){
            return "Document doesn't exists";
        }

        Optional<DocumentApproverEntity> documentApproverEntity = approversRepo.findByDocumentAndStaff(documentEntity.get(), staffService.getCurrentStaff().get());
        if(documentApproverEntity == null){
            return "Approver doesn't exists";
        }

        documentApproverEntity.get().setApproved(!documentApproverEntity.get().isApproved());

        approversRepo.save(documentApproverEntity.get());
        return null;
    }
}
