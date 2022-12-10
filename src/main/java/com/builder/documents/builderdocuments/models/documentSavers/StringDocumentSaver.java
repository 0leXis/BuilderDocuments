package com.builder.documents.builderdocuments.models.documentSavers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.reader.StreamReader;

import com.builder.documents.builderdocuments.models.interfaces.IDocumentSaver;
import com.builder.documents.builderdocuments.models.interfaces.IXMLValidationService;

public class StringDocumentSaver implements IDocumentSaver {

    IXMLValidationService xmlService;

    private String content;
    private File xsdTemplate;

    public StringDocumentSaver(String content, File xsdTemplate, IXMLValidationService xmlService) {
        this.content = content;
        this.xsdTemplate = xsdTemplate;
        this.xmlService = xmlService;
    }

    @Override
    public String Save(String documentsPath) throws IOException {
        File documentFile;
        do{
            documentFile = new File(documentsPath + "document" + UUID.randomUUID() + ".xml");
        }
        while(documentFile.isFile());

        FileOutputStream stream = new FileOutputStream(documentFile, false);
        FileChannel channel = stream.getChannel();
        channel.write(StandardCharsets.UTF_8.encode(content));
        stream.close();

        return documentFile.getPath();
    }

    @Override
    public String Validate() {
        if(xsdTemplate == null)
            return null;
        StringReader reader = new StringReader(content);
        try{
            Validator validator = xmlService.getValidator(xsdTemplate);
            validator.validate(new StreamSource(reader));
        }
        catch(SAXException e){
            return "Validation error: " + e.getMessage();
        }
        catch(IOException e){
            return "Validation error: " + e.getMessage();
        }
        finally{
            reader.close();
        }
        return null;
    }
    
}
