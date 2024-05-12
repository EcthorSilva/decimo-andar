create schema DECIMO_ANDAR;

use DECIMO_ANDAR;

CREATE TABLE DECIMO_ANDAR.Imovel (
    id BIGINT PRIMARY KEY auto_increment,
    tipo VARCHAR(255),
    quantidadeQuartos INTEGER,
    metrosQuadrados DOUBLE ,
    cep VARCHAR(10) ,
    logradouro VARCHAR(255) ,
    descricao TEXT,
    quantidadeBanheiros INTEGER
);

CREATE TABLE DECIMO_ANDAR.Usuario (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NOME_COMPLETO TEXT,
    SENHA VARCHAR(100),
    DATA_NASCIMENTO TEXT,
    EMAIL TEXT,
    CPF_CNPJ TEXT,
    TELEFONE TEXT
);