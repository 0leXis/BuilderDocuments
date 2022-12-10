package com.builder.documents.builderdocuments.models.services;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.builder.documents.builderdocuments.models.interfaces.IXMLValidationService;

@Service
public class XMLValidationService implements IXMLValidationService {
    public Validator getValidator(File xsdFile) throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(xsdFile);
        Schema schema = factory.newSchema(schemaFile);
        return schema.newValidator();
    }
}
