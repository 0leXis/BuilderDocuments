function editStaff(id){
    setValueFromValue("addEditModeField", "edit");

    setValueFromCell("addEditName", `staff${id}name`);
    setValueFromCell("addEditLastname", `staff${id}lastname`);
    setValueFromCellData("addEditPosition", `staff${id}position`);
    setValueFromCell("addEditSalary", `staff${id}salary`);

    setValueFromValue("addEditIdStaffField", id);

    showFormDialog("addEditStaffFormContainer");
}

function addStaff(id){
    setValueFromValue("addEditModeField", "add");

    clearValue("addEditName");
    clearValue("addEditLastname");
    setValueFromValue("addEditPosition", 1);
    clearValue("addEditSalary");

    setValueFromValue("addEditIdLoginInfoField", id);

    showFormDialog("addEditStaffFormContainer");
}

function cancelAddEditStaff(){
    hideFormDialog("addEditStaffFormContainer");
}

function deleteStaff(id){
    setValueFromValue("deleteIdStaffField", id);

    showFormDialog("deleteStaffFormContainer");
}

function cancelDeleteStaff(){
    hideFormDialog("deleteStaffFormContainer");
}