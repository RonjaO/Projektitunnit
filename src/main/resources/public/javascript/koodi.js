var aloita = document.getElementById("aloita");
aloita.disabled = true;
var radiobuttonit = document.getElementsById("projekti");

console.log("Aloita on himmennetty");

for (var i = 0; i < radiobuttonit.length; i00) {
    radiobuttonit[i].addEventListener('click', function(eventInfo) {
        aloita.disabled = false;
        console.log("Aloita pitäis toimia");
    }, false)
}