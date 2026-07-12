package prolab.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import prolab.system.domain.Cliente;
import prolab.system.domain.DocumentoCliente;
import prolab.system.request.ClienteRequest;
import prolab.system.request.DocumentoClienteRequest;
import prolab.system.response.ClienteResponse;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    // converte ClienteRequest → entidade Cliente
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "documentos", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Cliente toCliente(ClienteRequest clienteRequest);

    // converte entidade Cliente → ClienteResponse
    ClienteResponse toClienteResponse(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "documentos", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarCliente(ClienteRequest request, @MappingTarget Cliente cliente);

}
