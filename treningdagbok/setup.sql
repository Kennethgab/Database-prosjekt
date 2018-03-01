CREATE TABLE Treningsøkt(øktId NOT NULL AUTO_INCREMENT,
	dato DATE,
	varighet INT,
	tidspunkt TIME,
	form INT,
	prestasjon INT,
	LøpeNr INT ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY(øktId))


CREATE TABLE Notat(løpeNr NOT NULL AUTO_INCREMENT,
	treningsformål VARCHAR(20),
	øktbeskrivelse VARCHAR(50),
	resultat VARCHAR(20),
	PRIMARY KEY(løpeNr))

CREATE TABLE Øvelsesgruppe(gruppeId NOT NULL AUTO_INCREMENT,
	gruppebeskrivelse VARCHAR(50)
	PRIMARY KEY(gruppeId))

CREATE TABLE Øvelse(øvelsesId NOT NULL AUTO_INCREMENT,
	øvelseNavn VARCHAR(30),
	øvelseBeskrivelse VARCHAR(50
	PRIMARY KEY(øvelsesID))

CREATE TABLE FastMontertApperat(øvelsesId REFERENCES Øvelse(øvelsesId),
	apperatId REFERENCES Apperat(apperatId))

CREATE TABLE Apparat(apperatId not NULL AUTO_INCREMENT,
	apperatNavn VARCHAR(50),
	apparatBeskrivelse VARCHAR(50)
	PRIMARY KEY(apperatId))


CREATE TABLE ØvelsePåØkt (
	øvelsesId	INT	NOT NULL,
	øktId		INT	NOT NULL,
	antallKilo	INT,
	antallSett	INT, 
	PRIMARY KEY (øvelsesId, øktId), 
	FOREIGN KEY (øvelsesId) REFERENCES Øvelse(øvelsesId)
		ON DELETE NO ACTION	ON UPDATE CASCADE,  
	FOREIGN KEY (øktId) REFERENCES TreningsØkt(øktId)
		ON DELETE NO ACTION	ON UPDATE CASCADE );

CREATE TABLE ØvelseTilhørerGruppe (
	gruppeId	INT	NOT NULL, 
	øvelsesId	INT	NOT NULL, 
	PRIMARY KEY (gruppeId, øvelsesId), 
	FOREIGN KEY (gruppeId) REFERENCES Øvelsesgruppe(gruppeId) 
		ON DELETE NO ACTION	ON UPDATE CASCADE,
	FOREIGN KEY (øvelsesId) REFERENCES Øvelse(øvelsesId)
		ON DELETE NO ACTION	ON UPDATE CASCADE );

CREATE TABLE FriØvelse (
        øvelsesId	INT		NOT NULL,
        resultat	VARCHAR(30),
        PRIMARY KEY (øvelsesId),
        FOREIGN KEY (øvelsesId) REFERENCES Øvelse(øvelsesId) 
                ON DELETE NO ACTION     ON UPDATE CASCADE );
	 
	  	
