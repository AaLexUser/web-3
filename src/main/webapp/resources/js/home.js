import {Graph} from "./graph.js";
$(document).ready(function() {
    let graph = new Graph(document.getElementById("graph"));
    graph.draw();
});