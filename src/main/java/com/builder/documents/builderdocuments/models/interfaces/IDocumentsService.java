package com.builder.documents.builderdocuments.models.interfaces;

import org.springframework.web.multipart.MultipartFile;

import com.builder.documents.builderdocuments.models.DocumentEntity;

public interface IDocumentsService {
    public String addDocument(DocumentEntity info, MultipartFile file, String secretKey);
    public String addDocument(DocumentEntity info, String documentText, String secretKey);

    public String editDocument(DocumentEntity info, MultipartFile file, String secretKey);
    public String editDocument(DocumentEntity info, String documentText, String secretKey);

    public String deleteDocument(DocumentEntity info);
}
