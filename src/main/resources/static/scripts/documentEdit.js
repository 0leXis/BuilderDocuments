const params = new Proxy(new URLSearchParams(window.location.search), {
    get: (searchParams, prop) => searchParams.get(prop),
  });

function addApprover() {
    let addApproverInput = document.getElementById("addApproverInput");
    let approversList = document.getElementById("approversList");

    let found = false;
    for(let i = 0; i < approversList.childElementCount; i++)
        if(approversList.children[i].value == addApproverInput.value)
        {
            let approverIdInput = document.getElementById("approverIdInput");
            approverIdInput.value = approversList.children[i].getAttribute("data-staffid");

            let addApproverForm = document.getElementById("addApproverForm");
            addApproverForm.submit();
            
            found = true;
            break;
        }
    if(!found)
        alert("Пользователь не найден");
}

function changeAssignee() {
    let changeAssigneeInput = document.getElementById("changeAssigneeInput");
    let assigneeList = document.getElementById("assigneeList");

    let found = false;
    for(let i = 0; i < assigneeList.childElementCount; i++)
        if(assigneeList.children[i].value == changeAssigneeInput.value)
        {
            let assigneeIdInput = document.getElementById("assigneeIdInput");
            assigneeIdInput.value = assigneeList.children[i].getAttribute("data-staffid");

            let changeAssigneeForm = document.getElementById("changeAssigneeForm");
            changeAssigneeForm.submit();
            
            found = true;
            break;
        }
    if(!found)
        alert("Пользователь не найден");
}

function deleteApprover(id){
    setValueFromValue("deleteIdApproverField", id);

    showFormDialog("deleteApproverFormContainer");
}

function cancelDeleteApprover(){
    hideFormDialog("deleteApproverFormContainer");
}