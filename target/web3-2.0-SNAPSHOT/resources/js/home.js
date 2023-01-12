import {Graph} from "./graph.js";
let graph = new Graph(document.getElementById("graph"));
let table = [];
let xHidden = document.getElementById("form:x");
let yInput = document.getElementById("form:y");
let rHidden = document.getElementById("form:r");
let submitButton = document.getElementById("form:submit-btn");
let xLabel = document.getElementById("form:x-label");
const xLim = {min:-5,max: 3};
const yLim = {min:-3,max:3};
const rLim = {min: 1,max: 3};
(function () {
    let selectedRs = [];

    window.selectedX = selectedX;
    function selectedX(value) {
        if ((value) != null) {
            console.log(xLabel);
            console.log(xHidden);
            console.log(value);
            xLabel.innerText = "Selected value: " + value;
            xHidden.value = value;
        }
    }
    window.selectedR = selectedR;
    function selectedR(value) {
        console.log(value);
        if (!isNaN(value)) {
            selectedRs.push(value);
        }
        if (selectedRs.length === 1)
            onRChanged(selectedRs[0]);
        console.log(selectedRs);
    }
    window.deselectedR = deselectedR;
    function deselectedR(value) {
        console.log(value);
        let index = selectedRs.indexOf(value);
        if (index !== -1) {
            selectedRs.splice(index, 1);
        }
        if (selectedRs.length === 1)
            onRChanged(selectedRs[0]);
        console.log(selectedRs);
    }

    function onRChanged(value) {
        if (!isNaN(value)) {
            rHidden.value = value;
        } else {
            rHidden.value = "";
        }
    }
})();

function  redrawGraph(){
    $('#result_table_data> tr ').each(function (i, element){
        let x = $(this).find("td>span.x").text().trim();
        let y = $(this).find(".y").text().trim();
        let r = $(this).find(".r").text().trim();
        let hit = $(this).find(".hit").text().trim() === 'Попадание';
        table[i]=[x, y, r, hit];
    });
    graph.draw();
    if(table.length>0){
        for(let i = 0; i < table.length; i++){
            graph.drawPoint(table[i][0], table[i][1], table[i][2], table[i][3]);
        }
    }
}
function onComplite() {
    xLabel.innerText = "Selected value: 123";
    xHidden.value = "";
    rHidden.value = "";
}

$(document).ready(function() {
    redrawGraph();
});

graph.canvas.addEventListener('click', (event) => {
    console.log("r:" + rHidden.value)
    console.log(!isNaN(rHidden.value))
    if (!isNaN(rHidden.value)&&rHidden.value<=rLim.max&&rHidden.value>=rLim.min){
        let r = rHidden.value;
        let x = graph.getMousePosition(event).x;
        let y = graph.getMousePosition(event).y;
        let w = graph.canvas.width;
        let h = graph.canvas.height;
        console.log(x, y);
        let scaleX = (x - w / 2) * r / (1 / 3 * w);
        let scaleY = -(y - h / 2) * r / (1 / 3 * h);
        if(scaleX>=xLim.max || scaleX<=xLim.min){
            alert("Error: X value:" + scaleX + " – is incorrect!");
            return;
        }
        if(scaleY>=yLim.max || scaleY<=yLim.min){
            alert("Error: Y value:" + scaleY + " – is incorrect!");
            return;
        }
        graph.drawPoint(scaleX,scaleY, r ,true)
        console.log("scaleX:" + scaleX);
        console.log("scaleY:" + scaleY);
        console.log("data sending...");
        xHidden.value = scaleX;
        xLabel.innerText = "Selected value: " + scaleX;
        yInput.value = scaleY;
        submitButton.click();
    } else {
        alert("Error: R field is incorrect!")
    }
});