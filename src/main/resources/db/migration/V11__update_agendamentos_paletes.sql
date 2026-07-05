ALTER TABLE agendamentos RENAME COLUMN volume_estimado TO quantidade_paletes;
ALTER TABLE agendamentos ALTER COLUMN quantidade_paletes TYPE INTEGER USING quantidade_paletes::INTEGER;