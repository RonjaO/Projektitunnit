console.log("moi");

document.getElementById("laheta").disabled = true;

var salasana2 = document.getElementById("salasana2");

salasana2.addEventListener('change', function(eventInformation) {
    vertaile(salasana2.value);    
}, false);

function vertaile(salasana2) {
    var salasana = document.getElementById("salasana").value;
    

    if (salasana != salasana2) {
        document.getElementById("virhe").innerHTML = "Salasanat eivät tästmää";
        console.log("Salasanoissa virhe, ") + salasana + " ja " + salasana2;
    } else if (salasana == salasana2){
        // Laitetaan lähetä-nappula käyttöön
        document.getElementById("laheta").disabled = false;
        document.getElementById("virhe").innerHTML = "";
    }
}