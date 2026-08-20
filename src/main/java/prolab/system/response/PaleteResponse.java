package prolab.system.response;

import prolab.system.enums.EstadoFisico;
import prolab.system.enums.TipoResiduo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaleteResponse(
        Long id,
        String ticket,
        Long recebimentoId,
        String numeroPalete,
        TipoResiduo tipo,
        BigDecimal peso,
        EstadoFisico estadoFisico,
        LocalDateTime createdAt
) {}
