package prolab.system.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecebimentoRequest(
        @NotNull
        Long agendamentoId,

        @NotBlank(message = "Placa do caminhão não pode ser nula ou vazia")
        String placaCaminhao,

        String modeloCaminhao,

        @NotBlank(message = "Motorista do caminhão não pode ser nulo ou vazio")
        String motoristaCaminhao,

        @NotNull
        LocalDateTime dataHoraRecebimento,  // renomeado

        BigDecimal pesoConferido,

        String observacoes
) {}
