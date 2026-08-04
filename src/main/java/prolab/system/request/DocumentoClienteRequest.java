package prolab.system.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import prolab.system.enums.TipoDocumento;

import java.time.LocalDate;

@Builder
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
