CREATE TABLE caminhoes(
    id BIGSERIAL PRIMARY KEY,
    plca VARCHAR(10) NOT NULL UNIQUE,
    modelo VARCHAR(50) NOT NULL,
    quantidade_palete INTEGER,
    motorista VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP
);