package prolab.system.response;

import lombok.Builder;
import prolab.system.enums.EstadoFisico;
import prolab.system.enums.TipoResiduo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaleteResponse(
        Long id,
        String ticket,
        Long recebimentoId,
        String prime,
        Integer numeroPalete,
        TipoResiduo tipo,
        BigDecimal peso,
        EstadoFisico estadoFisico,
        LocalDateTime createdAt
) {}
