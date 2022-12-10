const reader = new FileReader();
let templateTextElement = document.getElementById("templateTextElement");
let textFileElement = document.getElementById("textFileElement");

reader.addEventListener("load", () => {
    templateTextElement.innerText = reader.result;
  }, false);

textFileElement.addEventListener("change", function () {
    if(textFileElement.files[0]) {
        reader.readAsText(textFileElement.files[0]);
    }  
  });

function loadFromFile() {
    textFileElement.click();
}

