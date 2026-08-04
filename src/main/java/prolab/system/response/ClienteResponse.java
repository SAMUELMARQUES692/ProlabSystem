package prolab.system.response;

import java.time.LocalDateTime;

public record ClienteResponse(
        Long id,
        String razaoSocial,
        String cnpj,
        String contato,
        String endereco,
        Boolean ativo,
        LocalDateTime createdAt
) {
}
