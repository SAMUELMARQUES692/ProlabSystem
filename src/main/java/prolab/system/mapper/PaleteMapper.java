package prolab.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import prolab.system.entity.Palete;
import prolab.system.request.PaleteRequest;
import prolab.system.response.PaleteResponse;

@Mapper(componentModel = "spring")
public interface PaleteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticket", ignore = true)
    @Mapping(target = "numeroPalete", ignore = true)
    @Mapping(target = "recebimento", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Palete toPalete(PaleteRequest request);

    @Mapping(target = "recebimentoId", source = "recebimento.id")
    PaleteResponse toPaleteResponse(Palete palete);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticket", ignore = true)
    @Mapping(target = "numeroPalete", ignore = true)
    @Mapping(target = "recebimento", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarPalete(PaleteRequest request, @MappingTarget Palete palete);

}
