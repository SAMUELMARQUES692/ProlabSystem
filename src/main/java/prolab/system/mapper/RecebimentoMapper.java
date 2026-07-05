package prolab.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import prolab.system.domain.Recebimento;
import prolab.system.request.RecebimentoRequest;
import prolab.system.response.RecebimentoResponse;

@Mapper(componentModel = "spring")
public interface RecebimentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "caminhao", ignore = true)
    @Mapping(target = "prime", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Recebimento toRecebimento(RecebimentoRequest request);

    @Mapping(target = "agendamentoId", source = "agendamento.id")
    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "caminhaoId", source = "caminhao.id")
    RecebimentoResponse toRecebimentoResponse(Recebimento recebimento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "caminhao", ignore = true)
    @Mapping(target = "prime", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarRecebimento(RecebimentoRequest request, @MappingTarget Recebimento recebimento);



}
