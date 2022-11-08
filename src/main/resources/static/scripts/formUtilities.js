function setValueFromCell(fieldName, cellName){
    let cell = document.getElementById(cellName);
    let field = document.getElementById(fieldName);
    field.value = cell.innerText;
}

function setValueFromCellData(fieldName, cellName){
    let cell = document.getElementById(cellName);
    let field = document.getElementById(fieldName);
    field.value = cell.getAttribute("data-value");
}

function setValueFromValue(fieldName, value){
    let field = document.getElementById(fieldName);
    field.value = value;
}

function clearValue(fieldName){
    let field = document.getElementById(fieldName);
    field.value = "";
}

function showFormDialog(dialogName){
    let dialog = document.getElementById(dialogName);
    dialog.classList.remove("hidden");
}

function hideFormDialog(dialogName){
    let dialog = document.getElementById(dialogName);
    dialog.classList.add("hidden");
}