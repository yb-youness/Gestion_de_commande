BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "commande" (
	"id_commande"	INTEGER,
	"id_produit"	INTEGER NOT NULL,
	"idclient"	INTEGER NOT NULL,
	"adresse"	TEXT,
	"quantite"	INTEGER,
	"statut"	TEXT,
	"date_commande"	date,
	FOREIGN KEY("idclient") REFERENCES "client"("id_client"),
	PRIMARY KEY("id_commande"),
	FOREIGN KEY("id_produit") REFERENCES "produit"("ProduitId")
);
CREATE TABLE IF NOT EXISTS "client" (
	"id_client"	INTEGER,
	"Num_tel"	TEXT,
	"nom"	TEXT,
	"prenom"	TEXT,
	"email"	TEXT,
	"sexe"	TEXT,
	PRIMARY KEY("id_client")
);
CREATE TABLE IF NOT EXISTS "User" (
	"id"	INTEGER,
	"Username"	text,
	"email"	text,
	"pass"	text,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "produit" (
	"ProduitId"	INTEGER NOT NULL,
	"Libele"	varchar(255) NOT NULL,
	"Quantity"	INTEGER NOT NULL,
	"CategorieId"	INTEGER NOT NULL,
	"Prix"	Double NOT NULL,
	PRIMARY KEY("ProduitId"),
	FOREIGN KEY("CategorieId") REFERENCES "Categorie"("CatId")
);
CREATE TABLE IF NOT EXISTS "Categorie" (
	"CatId"	INTEGER NOT NULL,
	"LibeleCat"	varchar(255) NOT NULL,
	PRIMARY KEY("CatId")
);
COMMIT;
