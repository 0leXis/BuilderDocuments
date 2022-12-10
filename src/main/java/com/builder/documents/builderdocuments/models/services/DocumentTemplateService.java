package com.builder.documents.builderdocuments.models.services;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;
import javax.xml.namespace.QName;
import javax.xml.validation.Validator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.FilenameUtils;
import org.apache.xerces.xs.XSModel;
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
    
    @Override
    public String generateTemplatedDocument(DocumentTemplateEntity info, AtomicReference<String> result)
    {
        try{
            Document doc = loadXsdDocument(info.getPath());

            Element rootElem = doc.getDocumentElement();
            String targetName = null;
            String targetNamespace = null;
            if (rootElem != null && rootElem.getNodeName().equals("xs:schema")) 
            {
                targetNamespace = rootElem.getAttribute("targetNamespace");
                targetName = rootElem.getElementsByTagName("xs:element").item(0).getAttributes().getNamedItem("name").getTextContent();
            }
            else
                return "No root element found";

            XSModel xsModel = new XSParser().parse(info.getPath());

            XSInstance instance = new XSInstance();
            instance.minimumElementsGenerated = 1;
            instance.maximumElementsGenerated = 1;
            instance.generateDefaultAttributes = true;
            instance.generateOptionalAttributes = true;
            instance.maximumRecursionDepth = 0;
            instance.generateAllChoices = true;
            instance.showContentModel = true;
            instance.generateOptionalElements = true;

            StringWriter outWriter = new StringWriter();
            StreamResult resultStream = new StreamResult(outWriter);
            QName rootElement = new QName(targetNamespace, targetName);
            XMLDocument sampleXml = new XMLDocument(resultStream, true, 4, StandardCharsets.UTF_8.name());
            instance.generate(xsModel, rootElement, sampleXml);

            result.set(outWriter.getBuffer().toString());
            outWriter.close();
        }
        catch(SAXException e){
            return "Error parsing xsd: " + e.getMessage();
        }
        catch(Exception e){
            return "Unable to load xsd: " + e.getMessage();
        }

        return null;
    }

    private static Document loadXsdDocument(String inputName) throws ParserConfigurationException, IOException, SAXException{
        String filename = inputName;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        Document doc = null;

        DocumentBuilder builder = factory.newDocumentBuilder();
        File inputFile = new File(filename);
        doc = builder.parse(inputFile);

        return doc;
    }
}
