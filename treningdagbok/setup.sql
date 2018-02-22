CREATE TABLE Notat(
	løpenr INT NOT NULL,
	treningsformål VARCHAR(20),
	øktbeskrivelse VARCHAR(50),
	resultat VARCHAR(20),
	PRIMARY KEY(løpenr) );

CREATE TABLE Treningsøkt(
	øktid INT NOT NULL,
	dato DATE,
	varighet INT,
	tidspunkt TIME,
	form INT,
	prestasjon INT,
	løpenr INT NOT NULL,
	PRIMARY KEY(øktid),
	FOREIGN KEY (løpenr) REFERENCES Notat(løpenr)
		ON DELETE CASCADE ON UPDATE CASCADE );

CREATE TABLE ØvelsesGruppe(
	gruppeid INT NOT NULL,
	gruppebeskrivelse VARCHAR(50),
	PRIMARY KEY (gruppeid) );

CREATE TABLE Apparat(
	apparatid INT NOT NULL,
	apparatnavn VARCHAR(50),
	apparatbeskrivelse VARCHAR(50),
	PRIMARY KEY(apparatid) );

CREATE TABLE Øvelse(
	øvelsesid INT NOT NULL,
	øvelsenavn VARCHAR(30),
	øvelsebeskrivelse VARCHAR(50),
	apparatid INT,
	PRIMARY KEY(øvelsesid),
	FOREIGN KEY (apparatid) REFERENCES Apparat(apparatid)
		ON DELETE NO ACTION ON UPDATE CASCADE );

CREATE TABLE FriØvelse (
        øvelsesid INT NOT NULL,
	øktid INT NOT NULL,
        resultat VARCHAR(30),
	PRIMARY KEY (øvelsesid, øktid),
        FOREIGN KEY (øvelsesid) REFERENCES Øvelse(øvelsesid)
                ON DELETE NO ACTION ON UPDATE CASCADE,
	FOREIGN KEY (øktid) REFERENCES Treningsøkt(øktid)
		ON DELETE NO ACTION ON UPDATE CASCADE );

CREATE TABLE ApparatØvelse (
	øvelsesid INT NOT NULL,
	øktid INT NOT NULL,
	antallkilo INT,
	antallsett INT,
	resultat VARCHAR(30),
	PRIMARY KEY (øvelsesid, øktid),
	FOREIGN KEY (øvelsesid) REFERENCES Øvelse(øvelsesid)
		ON DELETE NO ACTION	ON UPDATE CASCADE,
	FOREIGN KEY (øktid) REFERENCES Treningsøkt(øktid)
		ON DELETE NO ACTION	ON UPDATE CASCADE );

CREATE TABLE ØvelseTilhørerGruppe (
	gruppeid	INT	NOT NULL,
	øvelsesid	INT	NOT NULL,
	PRIMARY KEY (gruppeid, øvelsesid),
	FOREIGN KEY (gruppeid) REFERENCES Øvelsesgruppe(gruppeId)
		ON DELETE NO ACTION	ON UPDATE CASCADE,
	FOREIGN KEY (øvelsesid) REFERENCES Øvelse(øvelsesId)
		ON DELETE NO ACTION	ON UPDATE CASCADE );
