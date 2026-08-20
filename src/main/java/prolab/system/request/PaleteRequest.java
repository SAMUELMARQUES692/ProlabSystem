package prolab.system.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import prolab.system.enums.EstadoFisico;
import prolab.system.enums.TipoResiduo;

import java.math.BigDecimal;

public record PaleteRequest(

        @NotNull
        Long recebimentoId,

        @NotNull
        TipoResiduo tipo,

        @NotNull
        @Positive
        BigDecimal peso,

        @NotNull
        EstadoFisico estadoFisico
) {}
