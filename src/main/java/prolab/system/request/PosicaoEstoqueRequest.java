package prolab.system.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import prolab.system.enums.StatusPosicao;

import java.math.BigDecimal;

@Builder
public record PosicaoEstoqueRequest(

        @NotBlank
        String codigo,

        BigDecimal capacidade,

        @NotNull(message = "O campo status é obrigatorio")
        StatusPosicao status
) {}
