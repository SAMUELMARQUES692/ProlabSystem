package prolab.system.response;

import lombok.Builder;
import prolab.system.enums.StatusResiduo;
import prolab.system.enums.TipoResiduo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ResiduoResponse(
        Long id,
        Long paleteId,
        String ticket,
        String prime,
        TipoResiduo tipo,
        BigDecimal peso,
        Long posicaoId,
        StatusResiduo status,
        String mtrVinculado,
        LocalDateTime dataDestinacao,
        LocalDateTime createdAt

) {}
