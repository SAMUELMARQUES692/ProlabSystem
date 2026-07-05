package prolab.system.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import prolab.system.enums.TipoDeDestruicao;

import java.time.LocalDateTime;

public record AgendamentoRequest(

        @NotNull
        Long clienteId,

        @NotBlank
        String tipoResiduo,

        @NotNull
        TipoDeDestruicao tipoDeDestruicao,

        @NotNull
        Integer quantidadePaletes,

        @NotNull
        @Future
        LocalDateTime dataHoraPrevista

) {
}
