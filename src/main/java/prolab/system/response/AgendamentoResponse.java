package prolab.system.response;

import java.time.LocalDateTime;

public record AgendamentoResponse(
        Long id,
        Long clienteId,
        String tipoResiduo,
        String tipoDeDestruicao,
        Integer quantidadePaletes,
        LocalDateTime dataHoraPrevista,
        String status,
        LocalDateTime createdAt
) {
}
