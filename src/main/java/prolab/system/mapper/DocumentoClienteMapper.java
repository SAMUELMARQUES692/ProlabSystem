package prolab.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import prolab.system.domain.DocumentoCliente;
import prolab.system.request.DocumentoClienteRequest;
import prolab.system.response.DocumentoClienteResponse;

@Mapper(componentModel = "spring")
public interface DocumentoClienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "recebimento", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DocumentoCliente toDocumentoCliente(DocumentoClienteRequest request);

    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "recebimentoId", source = "recebimento.id")
    DocumentoClienteResponse toDocumentoClienteResponse(DocumentoCliente documentoCliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "recebimento", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarDocumentoCliente(DocumentoClienteRequest request, @MappingTarget DocumentoCliente documentoCliente);
}
