package prolab.system.request;

import jakarta.validation.constraints.NotBlank;

public record CaminhaoRequest(

        @NotBlank(message = "Placa não pode ser nula ou vazia")
        String placa,

        String modelo,

        @NotBlank(message = "Motorista não pode ser nulo ou vazio")
        String motorista
) {
}
