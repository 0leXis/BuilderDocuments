function showDocument(id){
    window.location.replace("document?item=" + id);
}

function cancelUpload(){
    hideFormDialog("addDocumentFormContainer");
}

function uploadDocument(){
    showFormDialog("addDocumentFormContainer");
}