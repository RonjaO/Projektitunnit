CREATE TABLE Kayttaja (
    id SERIAL PRIMARY KEY,
    nimi varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    password varchar(50) NOT NULL
);

CREATE TABLE Projekti (
    id SERIAL PRIMARY KEY,
    kayttaja_id INTEGER REFERENCES Kayttaja(id),
    nimi varchar(50) NOT NULL,
    kuvaus varchar(200)
);

CREATE TABLE Tunti (
    id SERIAL PRIMARY KEY,
    kayttaja_id INTEGER REFERENCES Kayttaja(id),
    projekti_id INTEGER REFERENCES Projekti(id),
    alkuaika timestamp,
    loppuaika timestamp,
    kuvaus varchar(400)
);

