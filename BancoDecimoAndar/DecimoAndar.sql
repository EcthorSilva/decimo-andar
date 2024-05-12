create schema DECIMO_ANDAR;

use DECIMO_ANDAR;

CREATE TABLE DECIMO_ANDAR.Imovel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(255),
    tipoVenda VARCHAR(255),
    valor DECIMAL(10, 2),
    endereco VARCHAR(255),
    numero VARCHAR(20),
    cidade VARCHAR(255),
    uf VARCHAR(2),
    cep VARCHAR(10),
    numQuartos VARCHAR(255),
    numBanheiros VARCHAR(255),
    metrosQuadrados DECIMAL(10, 2),
    descricao TEXT,
    USER_ID INTEGER
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