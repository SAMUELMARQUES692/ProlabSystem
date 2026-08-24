package prolab.system.response;

import lombok.Builder;
import prolab.system.enums.TipoDeDestruicao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record RecebimentoResponse(
        Long id,
        Long agendamentoId,
        Long clienteId,
        Long caminhaoId,
        String prime,
        TipoDeDestruicao tipoDeDestruicao,
        LocalDateTime dataHoraRecebimento,
        BigDecimal pesoConferido,
        String observacoes,
        LocalDateTime createdAt
) {}
