function addOrder(){
    setValueFromValue("addEditModeField", "add");
    setValueFromValue("addEditName", "");
    setValueFromValue("addEditClient", "");
    setValueFromValue("addEditDescription", "");

    setValueFromValue("addEditIdOrdersField", 0);

    setValueFromValue("addEditSubmit", "Добавить");

    showFormDialog("addEditOrderFormContainer");
}

function editOrder(id){
    setValueFromValue("addEditModeField", "edit");
    setValueFromCell("addEditName", `order${id}name`);
    setValueFromCell("addEditClient", `order${id}client`);
    setValueFromCell("addEditDescription", `order${id}description`);

    setValueFromValue("addEditIdOrdersField", id);

    setValueFromValue("addEditSubmit", "Редактировать");

    showFormDialog("addEditOrderFormContainer");
}

function submitAddEditOrder() {
    let addEditClient = document.getElementById("addEditClient");
    let clientList = document.getElementById("clientList");

    let found = false;
    for(let i = 0; i < clientList.childElementCount; i++)
        if(clientList.children[i].value == addEditClient.value)
        {
            let clientIdInput = document.getElementById("clientIdInput");
            clientIdInput.value = clientList.children[i].getAttribute("data-clientid");

            let addEditOrderForm = document.getElementById("addEditOrderForm");
            addEditOrderForm.submit();
            
            found = true;
            break;
        }
    if(!found)
        alert("Клиент не найден");
}

function cancelAddEditOrder(){
    hideFormDialog("addEditOrderFormContainer");
}

function deleteOrder(id){
    setValueFromValue("deleteIdOrdersField", id);

    showFormDialog("deleteOrderFormContainer");
}

function cancelDeleteOrder(){
    hideFormDialog("deleteOrderFormContainer");
}

function createProject(id){
    setValueFromValue("orderIdProjectInput", id);

    showFormDialog("addProjectFormContainer");
}

function cancelAddProject(){
    hideFormDialog("addProjectFormContainer");
}