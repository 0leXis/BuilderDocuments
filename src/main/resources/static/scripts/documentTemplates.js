function editTemplate(id){
    window.location.replace("templateEdit?item=" + id);
}

function deleteTemplate(id){
    setValueFromValue("deleteIdDocumentTemplatesField", id);

    showFormDialog("deleteTemplateFormContainer");
}

function cancelDeleteTemplate(){
    hideFormDialog("deleteTemplateFormContainer");
}