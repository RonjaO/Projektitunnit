CREATE TABLE Kayttaja (
    id SERIAL PRIMARY KEY,
    nimi varchar(50) NOT NULL,
    email varchar(50) UNIQUE NOT NULL,
    password varchar(50) NOT NULL
);

CREATE TABLE Ryhma (
    id SERIAL PRIMARY KEY,
    nimi varchar(50) NOT NULL,
    johtaja integer REFERENCES Kayttaja(id) NOT NULL,
    kuvaus varchar(200)
);

CREATE TABLE Kayttaja_Ryhma (
    kayttaja integer REFERENCES Kayttaja(id)NOT NULL,
    ryhma integer REFERENCES Ryhma(id)NOT NULL
);

CREATE TABLE Projekti (
    id SERIAL PRIMARY KEY,
    omistaja_kayttaja integer REFERENCES Kayttaja(id),
    omistaja_ryhma integer REFEReNCES Ryhma(id),
    nimi varchar(50) NOT NULL,
    kuvaus varchar(200)
);

CREATE TABLE Tunti (
    id SERIAL PRIMARY KEY,
    kayttaja integer REFERENCES Kayttaja(id) NOT NULL,
    projekti_id INTEGER REFERENCES Projekti(id) NOT NULL,
    alkuaika timestamp NOT NULL,
    loppuaika timestamp,
    kuvaus varchar(400)
);

