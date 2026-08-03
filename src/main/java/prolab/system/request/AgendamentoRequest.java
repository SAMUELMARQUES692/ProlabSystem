package prolab.system.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import prolab.system.enums.TipoDeDestruicao;

import java.time.LocalDateTime;

@Builder
public record AgendamentoRequest(

        @NotNull
        Long clienteId,

        @NotBlank(message = "O tipo de resíduo não pode ser nulo ou vazio")
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
