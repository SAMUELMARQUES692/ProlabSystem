CREATE TABLE agendamentos(
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL REFERENCES clientes(id),
    tipo_residuo VARCHAR(100) NOT NULL,
    tipo_destruicao VARCHAR(30),
    volume_estimado NUMERIC(12,2),
    data_hora_prevista TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP
);

CREATE INDEX idx_agendamentos_cliente_id ON agendamentos(cliente_id);