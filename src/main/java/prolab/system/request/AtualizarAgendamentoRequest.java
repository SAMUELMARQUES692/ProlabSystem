package prolab.system.request;

import jakarta.validation.constraints.NotNull;
import prolab.system.enums.StatusAgendamento;

public record AtualizarAgendamentoRequest(

        @NotNull(message = "Para atualizar selecione um status")
        StatusAgendamento novoAgendamento
) {}
