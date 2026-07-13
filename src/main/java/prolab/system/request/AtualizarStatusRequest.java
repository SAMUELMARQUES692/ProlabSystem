package prolab.system.request;

import jakarta.validation.constraints.NotNull;
import prolab.system.enums.StatusResiduo;

public record AtualizarStatusRequest(

        @NotNull
        StatusResiduo novoStatus
) {}
