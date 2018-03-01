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

CREATE TABLE Øvelse(
	øvelsesId INT NOT NULL,
	øvelseNavn VARCHAR(30),
	øvelseBeskrivelse VARCHAR(50),
	PRIMARY KEY(øvelsesId) );

CREATE TABLE FastMontertApperat(
	øvelsesId INT REFERENCES Øvelse(øvelsesId),
	apperatId INT REFERENCES Apperat(apperatId) );

CREATE TABLE Apparat(
	apperatId INT NOT NULL,
	apperatNavn VARCHAR(50),
	apparatBeskrivelse VARCHAR(50),
	PRIMARY KEY(apperatId) );

CREATE TABLE FriØvelse (
        øvelsesId	INT NOT NULL,
        resultat	VARCHAR(30),
        PRIMARY KEY (øvelsesId),
        FOREIGN KEY (øvelsesId) REFERENCES Øvelse(øvelsesId) 
                ON DELETE NO ACTION ON UPDATE CASCADE );

CREATE TABLE ØvelsePåØkt (
	øvelsesId	INT	NOT NULL,
	øktId		INT	NOT NULL,
	antallKilo	INT,
	antallSett	INT, 
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
 	
