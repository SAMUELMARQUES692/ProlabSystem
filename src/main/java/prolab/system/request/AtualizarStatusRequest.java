package prolab.system.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import prolab.system.enums.StatusResiduo;

@Builder
public record AtualizarStatusRequest(

        @NotNull
        StatusResiduo novoStatus
) {}
