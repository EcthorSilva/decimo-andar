create database decimo_andar;

use decimo_andar;

CREATE TABLE TipoContrato (
    IdTipoContrato INT AUTO_INCREMENT PRIMARY KEY,
    Nome CHAR(45) NOT NULL
);

CREATE TABLE Status (
    IdStatus INT AUTO_INCREMENT PRIMARY KEY,
    Nome CHAR(45) NOT NULL
);

CREATE TABLE Registro (
    IdRegistro INT AUTO_INCREMENT PRIMARY KEY,
    Data CHAR,
    fk_Usuario_IdUsuario CHAR NOT NULL,
    fk_IdStatus CHAR NOT NULL,
    fk_IdImovel int NOT NULL
);

CREATE TABLE Imovel (
    IdImovel BIT PRIMARY KEY,
    fk_Anunciante_IdAnunciante char,
    fk_Usuario_IdUsuario CHAR,
    fk_TipoContrato_IdTipoContrato char,
    fk_Status_IdStatus CHAR
);

CREATE TABLE Usuario (
    IdUsuario CHAR PRIMARY KEY,
    Nome_Completo VARCHAR(100) NOT NULL,
    Data_de_Nascimento DATE,
    Email VARCHAR(100) NOT NULL,
	CPF_CNPJ VARCHAR(20) NOT NULL,
    Rua VARCHAR(100),
    Numero VARCHAR(10),
    Cep VARCHAR(10),
    Cidade VARCHAR(100),
   Estado CHAR(2)
);














