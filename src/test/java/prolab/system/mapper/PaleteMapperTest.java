package prolab.system.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import prolab.system.entity.Palete;
import prolab.system.enums.EstadoFisico;
import prolab.system.enums.TipoResiduo;
import prolab.system.request.PaleteRequest;
import prolab.system.response.PaleteResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaleteMapperTest {

    private final PaleteMapper mapper = Mappers.getMapper(PaleteMapper.class);

    @Test
    void toPalete() {
        PaleteRequest request = PaleteRequest.builder()
                .recebimentoId(1L)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.TEN)
                .estadoFisico(EstadoFisico.SOLIDO)
                .build();

        Palete palete = mapper.toPalete(request);

        assertNotNull(palete);

        assertEquals(request.tipo(), palete.getTipo());
        assertEquals(request.peso(), palete.getPeso());
        assertEquals(request.estadoFisico(), palete.getEstadoFisico());
    }

    @Test
    void toPaleteResponse() {
        Palete palete = Palete.builder()
                .id(1L)
                .ticket("Ticket Teste")
                .numeroPalete(3)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.ONE)
                .estadoFisico(EstadoFisico.SOLIDO)
                .createdAt(LocalDateTime.now())
                .build();

        PaleteResponse response = mapper.toPaleteResponse(palete);

        assertNotNull(response);
        assertEquals(palete.getId(), response.id());
        assertEquals(palete.getTicket(), response.ticket());
        assertEquals(palete.getTipo(), response.tipo());
        assertEquals(palete.getPeso(), response.peso());
        assertEquals(palete.getEstadoFisico(), response.estadoFisico());
        assertEquals(palete.getCreatedAt(), response.createdAt());

    }

    @Test
    void atualizarPalete() {
        PaleteRequest request = PaleteRequest.builder()
                .recebimentoId(1L)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.TEN)
                .estadoFisico(EstadoFisico.SOLIDO)
                .build();

        Palete palete = Palete.builder()
                .id(1L)
                .ticket("Ticket Teste")
                .numeroPalete(3)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.ONE)
                .estadoFisico(EstadoFisico.SOLIDO)
                .createdAt(LocalDateTime.now())
                .build();

        mapper.atualizarPalete(request, palete);

        assertNotNull(palete);

        assertEquals(request.tipo(), palete.getTipo());
        assertEquals(request.peso(), palete.getPeso());
        assertEquals(request.estadoFisico(), palete.getEstadoFisico());
    }
}