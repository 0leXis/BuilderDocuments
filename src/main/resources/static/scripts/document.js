function deleteDocument(id){
    setValueFromValue("deleteIdDocumentField", id);

    showFormDialog("deleteDocumentFormContainer");
}

function cancelDeleteDocument(){
    hideFormDialog("deleteDocumentFormContainer");
}

function uploadNew(id){
    setValueFromValue("uploadNewIdDocumentField", id);

    showFormDialog("uploadNewDocumentFormContainer");
}

function cancelUploadNew(){
    hideFormDialog("uploadNewDocumentFormContainer");
}

function printDocument(){
    let downloadLinkElement = document.getElementById("downloadLinkElement");
    let printWindow = window.open(downloadLinkElement.getAttribute("href"));
    printWindow.print();
}