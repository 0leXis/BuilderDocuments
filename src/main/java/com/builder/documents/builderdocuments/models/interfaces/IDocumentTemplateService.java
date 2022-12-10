package com.builder.documents.builderdocuments.models.interfaces;

import java.util.concurrent.atomic.AtomicReference;

import javax.xml.transform.stream.StreamResult;

import com.builder.documents.builderdocuments.models.DocumentTemplateEntity;

public interface IDocumentTemplateService {
    public String addTemplate(DocumentTemplateEntity info, String content);
    public String editTemplate(DocumentTemplateEntity info, String content);
    public String deleteTemplate(DocumentTemplateEntity info);

    public String generateTemplatedDocument(DocumentTemplateEntity info, AtomicReference<String> result);
}
