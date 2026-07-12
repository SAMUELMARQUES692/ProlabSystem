package prolab.system.request;

import jakarta.validation.constraints.NotNull;
import prolab.system.enums.TipoDocumento;

import java.time.LocalDate;

public record DocumentoClienteRequest(

        @NotNull
        Long clienteId,

        Long recebimentoId,

        @NotNull
        TipoDocumento tipo,

        String numero,

        String arquivoUrl,

        LocalDate dataEmissao,

        String observacoes


) {}
