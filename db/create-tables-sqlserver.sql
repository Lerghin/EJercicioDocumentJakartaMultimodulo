CREATE TABLE TECDocument (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    JPADC VARCHAR(32),
    Nombre VARCHAR(128) NOT NULL,
    Fecha DATETIME NOT NULL,
    Estado VARCHAR(32),
    Departamento VARCHAR(64)
);

ALTER TABLE TECDocument 
ADD Proveedor VARCHAR(64) NULL,
    Numero_Referencia VARCHAR(64) NULL,
    curier VARCHAR(64) NULL;