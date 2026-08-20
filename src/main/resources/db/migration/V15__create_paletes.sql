CREATE TABLE paletes (
    id BIGSERIAL PRIMARY KEY,
    recebimento_id BIGINT NOT NULL REFERENCES recebimentos(id),
    ticket VARCHAR(255) NOT NULL UNIQUE,
    numero_palete INTEGER NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    peso NUMERIC(12,2) NOT NULL,
    estado_fisico VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP
);

CREATE INDEX idx_paletes_recebimento_id ON paletes(recebimento_id);