CREATE TABLE documentos_clientes(
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL REFERENCES clientes(id),
    recebimento_id BIGINT REFERENCES recebimentos(id),
    tipo VARCHAR(20) NOT NULL,
    numero VARCHAR(50),
    arquivo_url VARCHAR(500),
    data_emissao DATE,
    observacoes VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP
);

CREATE INDEX idx_documentos_clientes_cliente_id ON documentos_clientes(cliente_id);
CREATE INDEX idx_documentos_clientes_recebimento_id ON documentos_clientes(recebimento_id);