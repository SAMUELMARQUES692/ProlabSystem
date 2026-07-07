package prolab.system.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClienteRequest(

        @NotBlank(message = "Razão Social não pode ser nula ou vazia")
        String razaoSocial,

        @NotBlank(message = "CNPJ não pode ser nulo ou vazio")
        @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos numéricos")
        String cnpj,

        String contato,

        String endereco
) {
}
