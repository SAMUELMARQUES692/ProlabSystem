package prolab.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import prolab.system.entity.Caminhao;
import prolab.system.request.CaminhaoRequest;
import prolab.system.response.CaminhaoResponse;

@Mapper(componentModel = "spring")
public interface CaminhaoMapper {

    // converte CaminhaoRequest → entidade Caminhao
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Caminhao toCaminhao(CaminhaoRequest caminhaoRequest);

    // converte entidade Caminhao → CaminhaoResponse
    CaminhaoResponse toCaminhaoResponse(Caminhao caminhao);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarCaminhao(CaminhaoRequest request, @MappingTarget Caminhao caminhao);

}
