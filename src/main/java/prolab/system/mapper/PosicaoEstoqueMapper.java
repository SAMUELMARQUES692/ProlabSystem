package prolab.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import prolab.system.entity.PosicaoEstoque;
import prolab.system.request.PosicaoEstoqueRequest;
import prolab.system.response.PosicaoEstoqueResponse;

@Mapper(componentModel = "spring")
public interface PosicaoEstoqueMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PosicaoEstoque toPosicaoEstoque(PosicaoEstoqueRequest request);

    PosicaoEstoqueResponse toPosicaoEstoqueResponse(PosicaoEstoque posicaoEstoque);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarPosicaoEstoque(PosicaoEstoqueRequest request, @MappingTarget PosicaoEstoque posicaoEstoque);
}
