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
    @Mapping(target = "palete", ignore = true)
    @Mapping(target = "posicaoEstoque", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Residuo toResiduo(ResiduoRequest request);

    @Mapping(target = "paleteId", source = "palete.id")
    @Mapping(target = "posicaoId", source = "posicaoEstoque.id")
    @Mapping(target = "ticket", source = "palete.ticket")
    @Mapping(target = "prime", source = "palete.recebimento.prime")
    @Mapping(target = "tipo", source = "palete.tipo")
    @Mapping(target = "peso", source = "palete.peso")
    ResiduoResponse toResiduoResponse(Residuo residuo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "palete", ignore = true)
    @Mapping(target = "posicaoEstoque", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarResiduo(ResiduoRequest request, @MappingTarget Residuo residuo);

}
