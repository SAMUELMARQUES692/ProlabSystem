package prolab.system.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DocumentoClienteRequest(

        @NotNull
        Long clienteId,

        Long recebimentoId,

        @NotNull
        String tipo,

        String numero,

        String arquivoUrl,

        LocalDate dataEmissao,

        String observacoes


) {}
