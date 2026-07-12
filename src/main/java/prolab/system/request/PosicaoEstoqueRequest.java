package prolab.system.request;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record PosicaoEstoqueRequest(

        @NotBlank
        String codigo,

        BigDecimal capacidade
) {}
