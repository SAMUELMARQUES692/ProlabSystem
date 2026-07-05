CREATE TABLE recebimentos (
    id BIGSERIAL PRIMARY KEY,
    agendamento_id BIGINT NOT NULL REFERENCES agendamentos(id),
    cliente_id BIGINT NOT NULL REFERENCES clientes(id),
    caminhao_id BIGINT NOT NULL REFERENCES caminhoes(id),
    prime VARCHAR(20) NOT NULL UNIQUE,
    data_hora_recebimento TIMESTAMP NOT NULL,
    peso_conferido NUMERIC(12,2),
    observacoes VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP
);

CREATE INDEX idx_recebimentos_agendamento_id ON recebimentos(agendamento_id);
CREATE INDEX idx_recebimentos_cliente_id ON recebimentos(cliente_id);
CREATE INDEX idx_recebimentos_caminhao_id ON recebimentos(caminhao_id);