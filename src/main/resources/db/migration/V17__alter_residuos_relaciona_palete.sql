ALTER TABLE residuos DROP COLUMN tipo_residuo;
ALTER TABLE residuos DROP COLUMN quantidade;
ALTER TABLE residuos DROP COLUMN recebimento_id;

ALTER TABLE residuos ADD COLUMN palete_id BIGINT NOT NULL REFERENCES paletes(id);
ALTER TABLE residuos ADD CONSTRAINT uq_residuos_palete_id UNIQUE (palete_id);

CREATE INDEX idx_residuos_palete_id ON residuos(palete_id);