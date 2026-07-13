package prolab.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import prolab.system.entity.Residuo;
import prolab.system.request.ResiduoRequest;
import prolab.system.response.ResiduoResponse;

@Mapper(componentModel = "spring")
public interface ResiduoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recebimento", ignore = true)
    @Mapping(target = "posicaoEstoque", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Residuo toResiduo(ResiduoRequest request);

    @Mapping(target = "recebimentoId", source = "recebimento.id")
    @Mapping(target = "posicaoId", source = "posicaoEstoque.id")
    ResiduoResponse toResiduoResponse(Residuo residuo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recebimento", ignore = true)
    @Mapping(target = "posicaoEstoque", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarResiduo(ResiduoRequest request, @MappingTarget Residuo residuo);

}
