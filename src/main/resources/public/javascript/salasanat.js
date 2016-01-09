console.log("moi");

var salasana2 = document.getElementById("salasana2");

salasana2.addEventListener('change', function(eventInformation) {
    vertaile(salasana2.value);    
}, false);

function vertaile(salasana2) {
    var salasana = document.getElementById("salasana").value;
    

    if (salasana != salasana2) {
        document.getElementById("virhe").innerHTML = "Salasanat eivät tästmää";
        console.log("Salasanoissa virhe, ") + salasana + " ja " + salasana2;
    }   
}