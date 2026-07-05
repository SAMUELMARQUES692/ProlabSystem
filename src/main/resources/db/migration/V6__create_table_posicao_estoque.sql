CREATE TABLE posicao_estoques(
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(100) NOT NULL,
    capacidade NUMERIC(12,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP
);