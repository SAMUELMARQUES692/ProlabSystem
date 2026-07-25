package prolab.system.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import prolab.system.enums.StatusPosicao;

import java.math.BigDecimal;

public record PosicaoEstoqueRequest(

        @NotBlank
        String codigo,

        BigDecimal capacidade,

        @NotNull(message = "O campo status é obrigatorio")
        StatusPosicao status
) {}
