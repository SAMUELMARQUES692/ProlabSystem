package prolab.system.response;

import java.time.LocalDateTime;

public record CaminhaoResponse(
        Long id,
        String placa,
        String modelo,
        String motorista,
        LocalDateTime createdAt
) {
}
