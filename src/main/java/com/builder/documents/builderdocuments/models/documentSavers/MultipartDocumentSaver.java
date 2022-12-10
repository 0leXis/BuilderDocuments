package com.builder.documents.builderdocuments.models.documentSavers;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.builder.documents.builderdocuments.models.interfaces.IDocumentSaver;

public class MultipartDocumentSaver implements IDocumentSaver {

    private MultipartFile file;

    public MultipartDocumentSaver(MultipartFile file){
        this.file = file;
    }

    @Override
    public String Save(String documentsPath) throws IOException {
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        File documentFile;
        do{
            documentFile = new File(documentsPath + "document" + UUID.randomUUID() + "." + fileExt);
        }
        while(documentFile.isFile());

        file.transferTo(documentFile);

        return documentFile.getPath();
    }

    @Override
    public String Validate() {
        if(file == null)
            return "Error uploading document";
        return null;
    }
}
