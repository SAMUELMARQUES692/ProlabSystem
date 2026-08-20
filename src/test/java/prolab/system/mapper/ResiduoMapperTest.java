package prolab.system.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import prolab.system.entity.Residuo;
import prolab.system.enums.StatusResiduo;
import prolab.system.enums.TipoDeDestruicao;
import prolab.system.request.ResiduoRequest;
import prolab.system.response.ResiduoResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ResiduoMapperTest {

    private final ResiduoMapper mapper = Mappers.getMapper(ResiduoMapper.class);

    @Test
    void toResiduo() {
        ResiduoRequest request = ResiduoRequest.builder()
                .mtrVinculado("123123")
                .build();

        Residuo residuo = mapper.toResiduo(request);

        assertNotNull(residuo);

        assertEquals(request.mtrVinculado(), residuo.getMtrVinculado());

    }

    @Test
    void toResiduoResponse() {
        Residuo residuo = Residuo.builder()
                .id(1L)
                .mtrVinculado("123123")
                .status(StatusResiduo.ARMAZENADO)
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        ResiduoResponse response = mapper.toResiduoResponse(residuo);

        assertNotNull(response);

        assertEquals(residuo.getId(), response.id());
        assertEquals(residuo.getMtrVinculado(), response.mtrVinculado());
        assertEquals(residuo.getStatus(), response.status());
        assertEquals(residuo.getDataDestinacao(), response.dataDestinacao());
        assertEquals(residuo.getCreatedAt() ,response.createdAt());
    }

    @Test
    void atualizarResiduo() {
        ResiduoRequest request = ResiduoRequest.builder()
                .mtrVinculado("123123")
                .build();

        Residuo residuo = Residuo.builder()
                .id(1L)
                .mtrVinculado("123123")
                .status(StatusResiduo.ARMAZENADO)
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        mapper.atualizarResiduo(request, residuo);

        assertNotNull(residuo);

        assertEquals(request.mtrVinculado(), residuo.getMtrVinculado());
    }
}