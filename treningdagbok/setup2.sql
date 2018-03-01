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
	 
	  	
