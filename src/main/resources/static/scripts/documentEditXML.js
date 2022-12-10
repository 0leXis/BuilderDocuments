const reader = new FileReader();
let documentTextElement = document.getElementById("documentTextElement");
let textFileElement = document.getElementById("textFileElement");
let selectTemplateElement = document.getElementById("selectTemplateElement");
let getTemplatedXMLRequest = new XMLHttpRequest();

function loadTemplate(id){
    if(id > 0){
        getTemplatedXMLRequest.open('GET', `/templatedXML?templateId=${id}`);
        getTemplatedXMLRequest.send();
    }
}

selectTemplateElement.addEventListener("change", () => {
    loadTemplate(selectTemplateElement.value);
  }, false);

reader.addEventListener("load", () => {
    documentTextElement.value = reader.result;
  }, false);

textFileElement.addEventListener("change", function () {
    if(textFileElement.files[0]) {
        reader.readAsText(textFileElement.files[0]);
    }  
  });

getTemplatedXMLRequest.onload = function() {
    if (getTemplatedXMLRequest.status == 200) {
        documentTextElement.value = getTemplatedXMLRequest.responseText;
    }
};

function loadFromFile() {
    textFileElement.click();
}