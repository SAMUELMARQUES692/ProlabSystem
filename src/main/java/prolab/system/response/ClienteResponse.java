package prolab.system.response;

public record ClienteResponse(
        Long id,
        String razaoSocial,
        String cnpj,
        String contato,
        String endereco,
        Boolean ativo
) {
}
