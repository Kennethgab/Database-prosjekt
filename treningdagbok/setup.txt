CREATE TABLE Notat(
	løpeNr INT NOT NULL,
	treningsformål VARCHAR(20),
	øktbeskrivelse VARCHAR(50),
	resultat VARCHAR(20),
	PRIMARY KEY(løpeNr) );

CREATE TABLE Treningsøkt(
	øktId INT NOT NULL,
	dato DATE,
	varighet INT,
	tidspunkt TIME,
	form INT,
	prestasjon INT,
	løpeNr INT NOT NULL,
	PRIMARY KEY(øktId), 
	FOREIGN KEY (løpeNr) REFERENCES Notat(løpeNr)
		ON DELETE CASCADE ON UPDATE CASCADE );

CREATE TABLE Øvelsesgruppe(
	gruppeId INT NOT NULL,
	gruppebeskrivelse VARCHAR(50),
	PRIMARY KEY (gruppeId) );

CREATE TABLE Apparat(
	apparatId INT NOT NULL,
	apparatNavn VARCHAR(50),
	apparatBeskrivelse VARCHAR(50),
	PRIMARY KEY(apparatId) );

CREATE TABLE Øvelse(
	øvelsesId INT NOT NULL,
	øvelseNavn VARCHAR(30),
	øvelseBeskrivelse VARCHAR(50),
	apparatId INT, 
	PRIMARY KEY(øvelsesId), 
	FOREIGN KEY (apparatId) REFERENCES Apparat(apparatId)
		ON DELETE NO ACTION ON UPDATE CASCADE );

CREATE TABLE FriØvelse (
        øvelsesId INT NOT NULL,
	øktId INT NOT NULL, 
        resultat VARCHAR(30),
	PRIMARY KEY (øvelsesId, øktId),
        FOREIGN KEY (øvelsesId) REFERENCES Øvelse(øvelsesId) 
                ON DELETE NO ACTION ON UPDATE CASCADE, 
	FOREIGN KEY (øktId) REFERENCES Treningsøkt(øktId)
		ON DELETE NO ACTION ON UPDATE CASCADE );

CREATE TABLE ApparatØvelse (
	øvelsesId INT NOT NULL,
	øktId INT NOT NULL,
	antallKilo INT,
	antallSett INT,
	resultat VARCHAR(30), 
	PRIMARY KEY (øvelsesId, øktId), 
	FOREIGN KEY (øvelsesId) REFERENCES Øvelse(øvelsesId)
		ON DELETE NO ACTION	ON UPDATE CASCADE,  
	FOREIGN KEY (øktId) REFERENCES Treningsøkt(øktId)
		ON DELETE NO ACTION	ON UPDATE CASCADE );

CREATE TABLE ØvelseTilhørerGruppe (
	gruppeId	INT	NOT NULL, 
	øvelsesId	INT	NOT NULL, 
	PRIMARY KEY (gruppeId, øvelsesId), 
	FOREIGN KEY (gruppeId) REFERENCES Øvelsesgruppe(gruppeId) 
		ON DELETE NO ACTION	ON UPDATE CASCADE,
	FOREIGN KEY (øvelsesId) REFERENCES Øvelse(øvelsesId)
		ON DELETE NO ACTION	ON UPDATE CASCADE );
