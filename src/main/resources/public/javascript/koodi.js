var aloita = document.getElementById("aloita");
aloita.disabled = true;
console.log("Aloita on himmennetty");

var radiobuttonit = document.getElementsByTagName("input");

for (i = 0; i < radiobuttonit.length; i++) {
    if (radiobuttonit[i].type.toLowerCase() == "radio") {
        radiobuttonit[i].addEventListener("clisck", function(eventInfo) {
            aloita.disabled = false;
            console.log("Aloita on käytössä");
            
        });
    }
}