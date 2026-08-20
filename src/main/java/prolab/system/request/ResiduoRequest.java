package prolab.system.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ResiduoRequest(

        @NotNull
        Long paleteId,

        @NotNull
        Long posicaoId,

        String mtrVinculado
) {}
