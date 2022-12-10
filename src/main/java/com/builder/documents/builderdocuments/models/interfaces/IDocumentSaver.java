package com.builder.documents.builderdocuments.models.interfaces;

import java.io.IOException;

public interface IDocumentSaver {
    String Save(String documentsPath) throws IOException;
    String Validate();
}
