function deleteDocument(id){
    setValueFromValue("deleteIdDocumentField", id);

    showFormDialog("deleteDocumentFormContainer");
}

function cancelDeleteDocument(){
    hideFormDialog("deleteDocumentFormContainer");
}