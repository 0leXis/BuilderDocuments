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

getParams = Object.fromEntries(new URLSearchParams(location.search));

function sortBy(columnName){
    pathString = window.location.pathname + "?";

    if(getParams.sort == columnName){
        if(getParams.desc == undefined){
            getParams.desc = true;
        }
        else{
            delete getParams.desc;
        }
    }
    else{
        if(getParams.desc != undefined){
            delete getParams.desc;
        }
        getParams["sort"] = columnName;
    }

    Object.keys(getParams).forEach(function(key) {
        pathString += key + "=" + getParams[key] + "&";
    });

    window.location.replace(pathString.substring(0, pathString.length - 1));
}