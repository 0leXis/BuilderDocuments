package com.builder.documents.builderdocuments.models.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.DocumentTemplateEntity;
import com.builder.documents.builderdocuments.models.StaffEntity;
import com.builder.documents.builderdocuments.models.documentSavers.MultipartDocumentSaver;
import com.builder.documents.builderdocuments.models.documentSavers.StringDocumentSaver;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentSaver;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentsService;
import com.builder.documents.builderdocuments.models.interfaces.IXMLValidationService;
import com.builder.documents.builderdocuments.models.repositories.DocumentApproversRepository;
import com.builder.documents.builderdocuments.models.repositories.DocumentsRepository;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;
import com.builder.documents.builderdocuments.models.repositories.StaffRepository;
import org.apache.commons.io.FilenameUtils;

@Service
public class DocumentsService implements IDocumentsService {
    public static String documentsPath = "E:\\Java\\BuilderDocuments\\builderdocuments\\src\\main\\resources\\storage\\"; //TODO To config

    @Autowired
    IXMLValidationService xmlService;
    @Autowired
    StaffRepository usersRepo;
    @Autowired
    LoginInfoRepository loginRepo;
    @Autowired
    DocumentsRepository documentsRepo;
    @Autowired
    DocumentApproversRepository approversRepo;

    public String addDocument(DocumentEntity info, IDocumentSaver saver, String secretKey) {
        //TODO Template check
        //TODO Localization
        if(info.getName().length() < 1)
            return "Enter name";
        if(info.getDescription() == null)
            info.setDescription("Нет описания");

        String validationError = saver.Validate();
        if(validationError != null)
            return "Error uploading document";

        info.setDateCreated(new Date());
        info.setDateModified(new Date());

        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<StaffEntity> creator = usersRepo.findByLoginInfo(loginRepo.findByLogin(currentUser.getUsername()));
        if(!creator.isPresent())
            return "Creator does not exists";
        info.setCreator(creator.get());
        
        //TODO secret key check
        info.setHash(secretKey);

        String documentFile;
        try{
            documentFile = saver.Save(secretKey);
        }
        catch(IOException e){
            return "Error uploading document";
        }

        info.setPath(documentFile);

        documentsRepo.save(info);
        return null;
    }

    @Override
    public String addDocument(DocumentEntity info, MultipartFile file, String secretKey)
    {
        info.setFormalized(false);
        return addDocument(info, new MultipartDocumentSaver(file), secretKey);
    }

    @Override
    public String addDocument(DocumentEntity info, String documentText, String secretKey)
    {
        info.setFormalized(true);
        DocumentTemplateEntity template = info.getTemplate();
        if(template == null)
            return addDocument(info, new StringDocumentSaver(documentText, null, xmlService), secretKey);
        else
            return addDocument(info, new StringDocumentSaver(documentText, new File(template.getPath()), xmlService), secretKey);
    }

    @Override
    public String editDocument(DocumentEntity info, MultipartFile file, String secretKey) {
        info.setDateModified(new Date());
        return null;
    }

    @Override
    public String deleteDocument(DocumentEntity info) {
        Optional<DocumentEntity> documentEntity = documentsRepo.findById(info.getIdDocument());
        if(documentEntity == null){
            return "Document doesn't exists";
        }

        try{
            Files.deleteIfExists(Paths.get(info.getPath()));
            //TODO WHY EXCEPTOION??????
        }
        catch (Exception e) {}

        approversRepo.deleteByDocument(documentEntity.get());
        documentsRepo.delete(documentEntity.get());

        return null;
    }
    
}
