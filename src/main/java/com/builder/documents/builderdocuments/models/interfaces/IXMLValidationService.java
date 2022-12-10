package com.builder.documents.builderdocuments.models.interfaces;

import java.io.File;

import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public interface IXMLValidationService {
    Validator getValidator(File xsdFile) throws SAXException;
}
