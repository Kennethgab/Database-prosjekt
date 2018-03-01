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


