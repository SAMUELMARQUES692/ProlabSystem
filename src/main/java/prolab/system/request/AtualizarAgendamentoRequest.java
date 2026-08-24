package prolab.system.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import prolab.system.enums.StatusAgendamento;

@Builder
public record AtualizarAgendamentoRequest(

        @NotNull(message = "Para atualizar selecione um status")
        StatusAgendamento novoAgendamento
) {}
