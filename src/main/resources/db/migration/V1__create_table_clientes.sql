CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    razao_social VARCHAR(250) NOT NULL,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    telefone_contato VARCHAR(250),
    endereco VARCHAR(500),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP
);