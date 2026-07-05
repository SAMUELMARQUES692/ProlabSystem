CREATE TABLE residuos(
    id BIGSERIAL PRIMARY KEY,
    recebimento_id BIGINT NOT NULL REFERENCES recebimentos(id),
    tipo_residuo VARCHAR(255) NOT NULL,
    quantidade DECIMAL(12, 2) NOT NULL,
    posicao_id BIGINT NOT NULL REFERENCES posicao_estoques(id),
    status VARCHAR(20) NOT NULL,
    mtr_vinculado VARCHAR(355),
    data_destinacao TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP
);

CREATE INDEX idx_residuos_recebimento_id ON residuos(recebimento_id);
CREATE INDEX idx_residuos_posicao_id ON residuos(posicao_id);
CREATE INDEX idx_residuos_status ON residuos(status);