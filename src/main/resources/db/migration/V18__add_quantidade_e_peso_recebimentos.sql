ALTER TABLE recebimentos ADD COLUMN quantidade_paletes INTEGER NOT NULL DEFAULT 0;
ALTER TABLE recebimentos ALTER COLUMN peso_conferido SET NOT NULL;
ALTER TABLE recebimentos ALTER COLUMN peso_conferido SET DEFAULT 0;