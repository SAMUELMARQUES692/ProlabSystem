package prolab.system.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecebimentoResponse(
        Long id,
        Long agendamentoId,
        Long clienteId,
        Long caminhaoId,
        String prime,
        LocalDateTime dataHoraRecebimento,
        BigDecimal pesoConferido,
        String observacoes,
        LocalDateTime createdAt
) {}
