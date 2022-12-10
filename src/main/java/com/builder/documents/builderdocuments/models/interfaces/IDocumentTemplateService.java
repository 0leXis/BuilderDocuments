package com.builder.documents.builderdocuments.models.interfaces;

import com.builder.documents.builderdocuments.models.DocumentTemplateEntity;

public interface IDocumentTemplateService {
    public String addTemplate(DocumentTemplateEntity info, String content);
    public String editTemplate(DocumentTemplateEntity info, String content);
    public String deleteTemplate(DocumentTemplateEntity info);
}
