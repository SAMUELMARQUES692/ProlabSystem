ALTER TABLE residuos DROP CONSTRAINT uq_residuos_posicao_id;

CREATE UNIQUE INDEX uq_residuos_posicao_id_ativo
    ON residuos (posicao_id)
    WHERE status <> 'DESTRUIDO';