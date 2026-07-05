package prolab.system.request;

import jakarta.validation.constraints.NotBlank;

public record CaminhaoRequest(

        @NotBlank
        String placa,

        String modelo,

        @NotBlank
        String motorista
) {
}
