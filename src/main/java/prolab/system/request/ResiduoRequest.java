package prolab.system.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ResiduoRequest(

        @NotNull
        Long recebimentoId,

        @NotBlank
        String tipoResiduo,

        @NotNull
        @Positive
        BigDecimal quantidade,

        @NotNull
        Long posicaoId,

        String mtrVinculado
) {}
