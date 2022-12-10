package com.builder.documents.builderdocuments.models.services;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.builder.documents.builderdocuments.models.DocumentTemplateEntity;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentTemplateService;
import com.builder.documents.builderdocuments.models.interfaces.IXMLValidationService;
import com.builder.documents.builderdocuments.models.repositories.DocumentTemplateRepository;

@Service
public class DocumentTemplateService implements IDocumentTemplateService {
    public static String templatesPath = "E:\\Java\\BuilderDocuments\\builderdocuments\\src\\main\\resources\\storage\\"; //TODO To config

    @Autowired
    DocumentTemplateRepository templateRepo;
    @Autowired
    IXMLValidationService xmlService;

    @Override
    public String addTemplate(DocumentTemplateEntity info, String content) {
        if(info.getName().length() < 1)
            return "Enter name";
        if(content == null || content.length() < 0)
            return "Enter template text";

        Optional<DocumentTemplateEntity> templateSameName = templateRepo.findByName(info.getName());
        if(templateSameName.isPresent())
            return "Template should have a unique name";

        File templateFile;
        do{
            templateFile = new File(templatesPath + "template" + UUID.randomUUID() + ".xsd");
        }
        while(templateFile.isFile());

        try{
            FileOutputStream stream = new FileOutputStream(templateFile, false);
            FileChannel channel = stream.getChannel();
            channel.write(StandardCharsets.UTF_8.encode(content));
            stream.close();
        }
        catch(IOException e){
            return "Error uploading document";
        }

        try{
            xmlService.getValidator(templateFile);
        }
        catch(SAXException e){
            try{
                Files.deleteIfExists(Paths.get(templateFile.getPath()));
                //TODO WHY EXCEPTOION??????
            }
            catch (IOException ex) {}
            return "Error parsing xsd: " + e.getMessage();
        }

        info.setPath(templateFile.getPath());
        templateRepo.save(info);

        return null;
    }

    @Override
    public String editTemplate(DocumentTemplateEntity info, String content) {
        Optional<DocumentTemplateEntity> template = templateRepo.findById(info.getIdDocumentTemplates());
        if(!template.isPresent())
            return "Template not found";

        if(info.getName().length() < 1)
            return "Enter name";
        if(content == null || content.length() < 0)
            return "Enter template text";

        Optional<DocumentTemplateEntity> templateSameName = templateRepo.findByName(info.getName());
        if(templateSameName.isPresent() && template != templateSameName)
            return "Template should have a unique name";

        File templateFile = new File(template.get().getPath());

        try{
            FileOutputStream stream = new FileOutputStream(templateFile, false);
            FileChannel channel = stream.getChannel();
            channel.write(StandardCharsets.UTF_8.encode(content));
            stream.close();
        }
        catch(IOException e){
            try{
                Files.deleteIfExists(Paths.get(templateFile.getPath()));
                //TODO WHY EXCEPTOION??????
            }
            catch (IOException ex) {}
            return "Error uploading document";
        }

        try{
            xmlService.getValidator(templateFile);
        }
        catch(SAXException e){
            return "Error parsing xsd: " + e.getMessage();
        }

        template.get().setName(info.getName());
        templateRepo.save(template.get());

        return null;
    }

    @Override
    public String deleteTemplate(DocumentTemplateEntity info) {
        Optional<DocumentTemplateEntity> template = templateRepo.findById(info.getIdDocumentTemplates());
        if(template == null){
            return "Template doesn't exists";
        }

        try{
            Files.deleteIfExists(Paths.get(info.getPath()));
            //TODO WHY EXCEPTOION??????
        }
        catch (Exception e) {}

        templateRepo.delete(template.get());

        return null;
    }
    
}
