package prolab.system.response;

import lombok.Builder;
import prolab.system.enums.StatusResiduo;

import java.time.LocalDateTime;

@Builder
public record ResiduoResponse(
        Long id,
        Long paleteId,
        String ticket,
        String prime,
        Long posicaoId,
        StatusResiduo status,
        String mtrVinculado,
        LocalDateTime dataDestinacao,
        LocalDateTime createdAt

) {}
