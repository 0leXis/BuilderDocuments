function editUser(id){
    let userLoginCell =  document.getElementById(`user${id}login`);
    let editLoginField =  document.getElementById("editLogin");
    editLoginField.value = userLoginCell.innerText;

    let editIdLoginInfoField = document.getElementById("editIdLoginInfoField");
    editIdLoginInfoField.value = id;

    let editUserFormContainer = document.getElementById("editUserFormContainer");
    editUserFormContainer.classList.remove("hidden");
}

function cancelEditUser(){
    let editUserFormContainer = document.getElementById("editUserFormContainer");
    editUserFormContainer.classList.add("hidden");
}

function deleteUser(id){
    let deleteIdLoginInfoField = document.getElementById("deleteIdLoginInfoField");
    deleteIdLoginInfoField.value = id;

    let deleteUserFormContainer = document.getElementById("deleteUserFormContainer");
    deleteUserFormContainer.classList.remove("hidden");
}

function cancelDeleteUser(){
    let deleteUserFormContainer = document.getElementById("deleteUserFormContainer");
    deleteUserFormContainer.classList.add("hidden");
}