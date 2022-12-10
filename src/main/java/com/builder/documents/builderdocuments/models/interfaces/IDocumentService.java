package com.builder.documents.builderdocuments.models.interfaces;

import com.builder.documents.builderdocuments.models.DocumentEntity;

public interface IDocumentService {
    String addApprover(String approverIdStr, String documentIdStr);
    String deleteApprover(String approverIdStr);
    String editDocument(DocumentEntity document);
    String changeAssignee(String assigneeIdStr, String documentIdStr);
    public String approveDocument(DocumentEntity info);
}
