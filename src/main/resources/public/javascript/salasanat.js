var salasana = document.getElementById("salasana").value;
var salasana2 = document.getElementById("salasana2").value;
console.log("moi");

if (salasana2 = "" ) {
    document.getElementById("virhe").innerHTML = "";
    console.log("Javascript tekee jotain");
} else if (salasana != salasana2) {
    document.getElementById("virhe").innerHTML = "Salasanat eivät tästmää";
    console.log("Salasanoissa virhe");
}