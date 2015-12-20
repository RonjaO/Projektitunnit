#1. JOHDANTO

##Järjestelmän tarkoitus

Projektituntiseuranta-järjestelmän tarkoituksena on tarjota ryhmille ja yksittäisille henkilöille mahdollisuus kirjata ja seurata projektikohtaisesti tekemiään työtunteja. Ensiksi toteutetaan yksilöiden tuntiseuranta, ryhmät myöhemmin. Järjestelmä sopii esimerkiksi opiskelijalle, joka haluaa tarkastella, kuinka paljon käyttää työtunteja kutakin kurssia kohden tai kenelle tahansa, joka haluaa pysyä ajantasalla tekemistään työtunneista ja siitä, kuinka paljon käyttää aikaa mihinkin. Ryhmätasolla ryhmän vetäjä voi helposti tarkastella ryhmäläisten tehtyjä tunteja. Ryhmän työtuntien seuranta on mahdollista sekä niin, että kaikki ryhmäläiset näkevät toistensa tekemät tunnit, tai vain ryhmän vetäjä näkee kaikkien tekemät tunnit, ryhmäläiset näkevät kukin vain omansa. Yksittäisellä henkilöllä tai ryhmällä voi olla useampia projekteja. Ryhmään voi lisätä henkilöitä, jotka ovat rekisteröityneet järjestelmän käyttäjiksi. Kuka tahansa käyttäjä voi perustaa uuden ryhmän ja kutsua ryhmään jäseniä.

Ajatuksena on, että työtuntejaan ei tarvitse itse laskea: järjestelmästä painaa nappia, kun aloittaa työnteon ja painaa jälleen nappia, kun lopettaa. Tehdyille työtunneille voi antaa kuvauksen siitä, minkä asioiden tekemiseen kyseisen ajan on tarkemmin käyttänyt (esim. ohjelmistoprojektin kohdalla koodaus/koodin lukeminen/suunnittelu...). Työtuntiraportista näkee projektin kokonaistuntimäärän sekä työtuntien kuvaukset. Mahdollisesti myös visuaalinen esitys viikkotasolla niin, että eri projektit näkyvät eri väreillä.


##Toteutus- ja toimintaympäristö

Järjestelmä toteutetaan Helsingin yliopiston tietojenkäsittelytieteen laitoksen users-palvelimella Tomcat-palvelimen alla. Ohjelmointikielenä on Java ja käytössä Spring Boot -framework. Tietokantana käytetään postgreSQL:ää. Yhteys tietokantaan hoidetaan JDBC-kirjaston Connection-oliolla.


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
[Kayttaja]-*[Tyotunti]
[Tyotunti]*-[Projekti]
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

Tietokohde: Tyotunti

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

[Kayttaja | (pk) sahkoposti; salasana; nimi]
[Projekti | (pk)id; (fk) kayttaja:Kayttaja; (fk)ryhma:Ryhma; nimi; kuvaus]
[Tyotunti | (pk)id; (fk)projekti;  (fk)kayttaja; alkamisaika; loppumisaika; kommentit]
[Ryhma | (pk)id; nimi; kuvaus]
[Kayttaja-Ryhma | (fk)kayttaja:Kayttaja; (fk)ryhmaID]

[Kayttaja]-*[Projekti]
[Projekti]1-*[Tyotunti]
[Kayttaja]1-*[Tyotunti]
[Ryhma]*-*[Kayttaja]
[Ryhma]-*[Projekti]

