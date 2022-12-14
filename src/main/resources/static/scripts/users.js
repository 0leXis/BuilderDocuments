function editUser(id){
    setValueFromCell("editLogin", `user${id}login`);

    setValueFromValue("editIdLoginInfoField", id);

    showFormDialog("editUserFormContainer");
}

function cancelEditUser(){
    hideFormDialog("editUserFormContainer");
}

function deleteUser(id){
    setValueFromValue("deleteIdLoginInfoField", id);

    showFormDialog("deleteUserFormContainer");
}

function cancelDeleteUser(){
    hideFormDialog("deleteUserFormContainer");
}

/* TODO Refactor all scripts and html */