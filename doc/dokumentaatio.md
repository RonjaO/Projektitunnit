#1. JOHDANTO

##Järjestelmän tarkoitus

Projektituntiseuranta-järjestelmän tarkoituksena on tarjota ryhmille ja yksittäisille henkilöille mahdollisuus kirjata ja seurata projektikohtaisesti tekemiään työtunteja. Ensiksi toteutetaan yksilöiden tuntiseuranta, ryhmät myöhemmin. Järjestelmä sopii esimerkiksi opiskelijalle, joka haluaa tarkastella, kuinka paljon käyttää työtunteja kutakin kurssia kohden tai kenelle tahansa, joka haluaa pysyä ajantasalla tekemistään työtunneista ja siitä, kuinka paljon käyttää aikaa mihinkin. Ryhmätasolla ryhmän vetäjä voi helposti tarkastella ryhmäläisten tehtyjä tunteja. Ryhmän työtuntien seuranta on mahdollista sekä niin, että kaikki ryhmäläiset näkevät toistensa tekemät tunnit, tai vain ryhmän vetäjä näkee kaikkien tekemät tunnit, ryhmäläiset näkevät kukin vain omansa. Yksittäisellä henkilöllä tai ryhmällä voi olla useampia projekteja. Ryhmään voi lisätä henkilöitä, jotka ovat rekisteröityneet järjestelmän käyttäjiksi. Kuka tahansa käyttäjä voi perustaa uuden ryhmän ja kutsua ryhmään jäseniä.

Ajatuksena on, että työtuntejaan ei tarvitse itse laskea: järjestelmästä painaa nappia, kun aloittaa työnteon ja painaa jälleen nappia, kun lopettaa. Tehdyille työtunneille voi antaa kuvauksen siitä, minkä asioiden tekemiseen kyseisen ajan on tarkemmin käyttänyt (esim. ohjelmistoprojektin kohdalla koodaus/koodin lukeminen/suunnittelu...). Työtuntiraportista näkee projektin kokonaistuntimäärän sekä työtuntien kuvaukset. Mahdollisesti myös visuaalinen esitys viikkotasolla niin, että eri projektit näkyvät eri väreillä.


##Toteutus- ja toimintaympäristö

Järjestelmä toteutetaan Herokun palvelimella. Ohjelmointikielenä on Java ja käytössä Spring Boot -framework. Tietokantana käytetään postgreSQL:ää, joka pyörii Herokun kautta Amazonin palvelimella. Yhteys tietokantaan hoidetaan JDBC-kirjaston kautta.


#2. YLEISKUVA JÄRJESTELMÄSTÄ

##Käyttötapauskaavio

[Käyttäjä]-(Kirjautuminen)
[Käyttäjä]-(Projektin luominen)<(Kirjautuminen)
[Käyttäjä]-(Tuntien kirjaus)<[Kirjautuminen)
[Käyttäjä]-(Työtuntiraportin katselu)

[Käyttäjä]-(Ryhmän luominen) -- Käyttäjästä tulee ryhmänjohtaja
[Ryhmänjohtaja]-(Henkilöiden lisäys ryhmään)
[Ryhmänjohtaja]-(Projektin luominen)
[Ryhmänjohtaja]-(Ryhmän työtuntiraportin katselu)
[Käyttäjä]-(Ryhmäkutsun hyväksyminen)

[Vierailija]-(Rekisteröityminen)


##Käyttäjäryhmät 

Käyttäjä
Käyttäjä on palveluun rekisteröitynyt käyttäjä, joka on kirjautunut sisään. Ryhmänjohtaja kuuluu myös tähän ryhmään.

Ryhmänjohtaja
Ryhmänjohtaja on käyttäjä, joka on luonut ryhmän. Hänellä on oikeus hallinnoida ryhmän asetuksia ja lisätä siihen henkilöitä.

Vierailija
Vierailija on kuka tahansa, joka tulee käymään palvelun verkkosivuilla.


##Käyttötapauskuvaukset

Käyttäjän käyttötapaukset
Projektin luominen:
Käyttäjä voi luoda itselleen projektin, jonka tunteja haluaa kirjata ja seurata. Projektille tulee antaa nimi.

Tuntien kirjaus:
Käyttäjä valitsee listasta, mitä projektia on tekemässä ja painaa aloitus-nappia. Lopetettuaan työskentelmisen käyttäjä painaa jälleen napia, jonka jälkeen hän voi halutessaan lisätä työtunneille kommentteja.

Työtuntiraportin katselu:
Käyttäjälle näytetään yleisraportti, jossa on kunkin projektin yhteistuntimäärä. Projektikohtaisesti käyttäjä voi tarkastella kaikkia tehtyjä työtunteja kommentteineen.

Ryhmän luominen:
Käyttäjä voi perustaa ryhmän, johon kutsuu muita käyttäjiä.

Ryhmäkutsun hyväksyminen:
Jos käyttäjälle on lähetetty kutsu johonkin ryhmään, hän voi hyväksyä tai olla hyväksymättä sitä. Kun käyttäjä hyväksyy ryhmäkutsun, hän näkee myös ryhmänsä projektit.

Muita käyttötapauksia:
Kirjautuminen


Ryhmänjohtajan käyttötapaukset
Henkilöiden lisäys projektiin:
Ryhmänjohtaja voi kutsua ryhmäänsä käyttäjiä, jotka ovat rekisteröityneet palveluun.

Projektin luominen:
Ryhmänjohtaja voi luoda ryhmälleen uuden projektin. Projekti tulee näkyviin kaikille ryhmän jäsenille. Ryhmänjohtaja antaa projektille nimen sekä valitsee, näkyykö tehdyt työtunnit kaikille vai voivatko ryhmän jäsenet tarkastella vain omia tuntejaan.

Ryhmän työtuntiraportin katselu:
Ryhmän asetuksista riippumatta ryhmänjohtaja näkee ryhmän tuntiraportista projektikohtaiset työmäärät sekä jokaisen ryhmän jäsenen tuntimäärät kussakin projektissa.

Muut käyttötapaukset:
Kirjautuminen


Vierailijan käyttötapaukset
Kirjautuminen, rekisteröityminen

#3. JÄRJESTELMÄN TIETOSISÄLTÖ

##Käsitekaavio
[Kayttaja]-*[Projekti]
[Kayttaja]-*[Tunti]
[Tunti]*-[Projekti]
[Kayttaja]*-*[Ryhma]
[Ryhma]-*[Projekti]

##Tietokohteiden kuvaukset
Tietokohde: Kayttaja

Atribuutti   | Arvojoukko    | kuvailu
-------------|---------------|--------
sähköposti   | merkkijono (max 50) | sähköposti, pääavain, Toimii käyttäjätunnuksena. Muotoa moi@example.com
salasana     |  merkkijono (max 50) | käyttäjän salasana
nimi         | merkkijono (max 50) | Käyttäjän nimi

Tietokohde: Projekti

Atribuutti   | Arvojoukko     | Kuvaus
-------------|----------------|--------
nimi         | Merkkijono (max 50) | Projektin nimi
kuvaus       | Merkkijono (max 200) | Valinnainen kuvaus projektille
kesto   | interval  | Projektien kaikkien tuntien kestot yhteensä

Tietokohde: Tunti

Atribuutti   | Arvojoukko     | Kuvaus
-------------|----------------|---------
aalkuaika    | timestamp      | Tyotunnin alkamisajankohta
loppuaika    | timestamp      | Työskentelyn loppuaika
kuvaus       | merkkijono [max 400) | Valinnainen kuvaus työtunnille

Tietokohde: Ryhma

Atribuutti   | Arvojoukko     |  Kuvaus
-------------|-----------------|--------
nimi         | merkkijono (max 50) | Ryhmän nimi
kuvaus       | merkkijono (max 200) | Valinnainen kuvaus ryhmlle


#4. Relaatiotietokantakaavio

```
[Kayttaja | (pk)id; sahkoposti; salasana; nimi]
[Projekti | (pk)id; (fk) kayttaja:Kayttaja; (fk)ryhma:Ryhma; nimi; kuvaus; kesto]
[Tunti | (pk)id; (fk)projekti:Projekti;  (fk)kayttaja:Kayttaja; alkamisaika; loppumisaika; kommentit]
[Ryhma | (pk)id; (fk)johtaja:Kayttaja; nimi; kuvaus]
[Kayttaja-Ryhma | (fk)kayttaja:Kayttaja; (fk)ryhma:Ryhma]

[Kayttaja]-*[Projekti]
[Projekti]1-*[Tunti]
[Kayttaja]1-*[Tunti]
[Ryhma]*-*[Kayttaja]
[Ryhma]-*[Projekti]
```


#5. Käynnistys/käyttöohje

Projektituntiseuranta-sovellus löytyy osoitteesta https://fierce-depths-9722.herokuapp.com. Testikäyttäjän käyttäjätunnus on "matti@example.com" ja salasana "matti123". Kaikki sovelluksen toiminnot vaativat sisäänkirjautumisen.


#6. Järjestelmän yleisrakenne

Projektituntiseuranta-sovelluksessa on pyritty noudattamaan mvc-mallia. Sen lisäksi kansiorakenne on Spring Bootin ja ThymeLeafin oletusten mukainen, eli esimerkiksi html-tiedostot löytyvät kansiosta /resources/templates ja muu selaimessa ajettava koodi (css ja JavaScript) löytyy kansion /resources/public alta omista kansioistaan. JavaScriptilla on toteutettu rekisteröityessä tehtävä salasanakenttien vertailu sekä työskentelyn aloitusnäkymässä varmistettu, ettei aloita-nappia voi painaa, jos projektia ei ole valittu.

Kaikki kontrollerit löytyvät kansiosta /controller. Ne on nimetty sen mukaan, mitä tietokannan tietokohteita niiden kautta käsitellään. Yhteydet tietokantaan löytyy niin ikään tietokohteittain nimetyistä tiedostoista kansiosta /repository. Tietokohteita vastaavat java-luokat löytyvät kansiosta /domain.

Sovelluksen autentikointi ja autorisointi -koodi löytyy tiedostosta SecurityConfiguration.java. Tietokannan osoite- ja käyttäjätunnukset löytyvät tiedostosta ProdProvfile.java. 


#7. Käyttöliittymä ja järjestelmäkomponentit

Kun käyttäjä on kirjautunut sisään, sivustolla on navigaatiopalkki, josta pääsee oleellisimpiin toimintoihin. Jos sivulta siirrytään muutoin toiseen näkymään, on se tässä merkitty nuolella (-> tai <-). Muutoin näkymät valitaan navigaatiopalkista.


signup: KayttajaController -> index

index ja login: Spring Securityn oletuskontrolleri (ohjaus määritelty SecurityConfiguration-luokassa) -> projektit

projektit: Projektien näyttäminen ProjektiController, työskentelyn aloitus/lopetus TuntiController (näkymä, jossa kirjataan työtunteja)

raportti: ProjektiController ja TuntiController -> 
    muokkaa_tuntia : TuntiController <- (palaa edelliseen näkymään)

kaikki_projektit: ProjektiController ->
    muokkaa_projektia: ProjektiController <-
    uusi_projekti ProjektiController -> projektit

ryhmat: RyhmaController ->
    uusi_ryhma: RyhmaController (toistaiseksi ei tosin tee mitään) 

