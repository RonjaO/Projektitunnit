console.log("moi");

document.getElementById("laheta").disabled = true;

var salasana2 = document.getElementById("salasana2");

salasana2.addEventListener('change', function(eventInformation) {
    vertaile(salasana2.value);    
}, false);

function vertaile(salasana2) {
    var salasana = document.getElementById("salasana").value;
    var virhe = document.getElementById("virhe");
    var laheta = document.getElementById("laheta");

    if (salasana != salasana2) {
        console.log("Salasanoissa virhe, ") + salasana + " ja " + salasana2;
        virhe.innerHTML = "Salasanat eivät täsmää"
        laheta.disabled = true;
    } else if (salasana == salasana2){
        virhe.innerHTML = "";
        // Laitetaan lähetä-nappula käyttöön
        laheta.disabled = false;
    }
}