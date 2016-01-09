var aloita = document.getElementById("aloita");
aloita.disabled = true;
console.log("Aloita on himmennetty");

if (document.getElementById("projekti")) {
    var radiobuttonit = document.getElementsById("projekti");

    for (var i = 0; i < radiobuttonit.length; i00) {
        radiobuttonit[i].addEventListener('click', function(eventInfo) {
            aloita.disabled = false;
            console.log("Aloita pitäis toimia");
        }, false)
    }
}