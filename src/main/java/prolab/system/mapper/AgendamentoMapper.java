package prolab.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import prolab.system.entity.Agendamento;
import prolab.system.request.AgendamentoRequest;
import prolab.system.response.AgendamentoResponse;

@Mapper(componentModel = "spring")
public interface AgendamentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Agendamento toAgendamento(AgendamentoRequest request);

    @Mapping(target = "clienteId", source = "cliente.id")
    AgendamentoResponse toAgendamentoResponse(Agendamento agendamento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarAgendamento(AgendamentoRequest request, @MappingTarget Agendamento agendamento);

}
