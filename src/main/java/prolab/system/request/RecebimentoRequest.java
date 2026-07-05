package prolab.system.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecebimentoRequest(
        @NotNull
        Long agendamentoId,

        @NotBlank
        String placaCaminhao,

        String modeloCaminhao,

        @NotBlank
        String motoristaCaminhao,

        @NotNull
        LocalDateTime dataHoraRecebimento,  // renomeado

        BigDecimal pesoConferido,

        String observacoes
) {}
