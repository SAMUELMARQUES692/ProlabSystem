package prolab.system.response;

import lombok.Builder;
import prolab.system.enums.StatusResiduo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ResiduoResponse(
        Long id,
        Long recebimentoId,
        String tipoResiduo,
        BigDecimal quantidade,
        Long posicaoId,
        StatusResiduo status,
        String mtrVinculado,
        LocalDateTime dataDestinacao,
        LocalDateTime createdAt

) {}
