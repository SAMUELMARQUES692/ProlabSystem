package prolab.system.response;

import prolab.system.enums.TipoDocumento;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DocumentoClienteResponse(
        Long id,
        Long clienteId,
        Long recebimentoId,
        TipoDocumento tipo,
        String numero,
        String arquivoUrl,
        LocalDate dataEmissao,
        String observacoes,
        LocalDateTime createdAt
) {}
