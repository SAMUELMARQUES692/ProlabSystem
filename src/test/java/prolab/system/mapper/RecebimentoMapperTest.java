package prolab.system.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import prolab.system.entity.Recebimento;
import prolab.system.request.RecebimentoRequest;
import prolab.system.response.RecebimentoResponse;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RecebimentoMapperTest {

    private final RecebimentoMapper mapper = Mappers.getMapper(RecebimentoMapper.class);

    @Test
    void toRecebimento() {
        RecebimentoRequest request = RecebimentoRequest.builder()
                .dataHoraRecebimento(LocalDateTime.now())
                .observacoes("Observacao")
                .build();

        Recebimento recebimento = mapper.toRecebimento(request);

        assertNotNull(recebimento);

        assertEquals(request.dataHoraRecebimento(), recebimento.getDataHoraRecebimento());
        assertEquals(request.observacoes(), recebimento.getObservacoes());
    }

    @Test
    void toRecebimentoResponse() {
        Recebimento recebimento = Recebimento.builder()
                .id(1L)
                .dataHoraRecebimento(LocalDateTime.now())
                .pesoConferido(null)
                .observacoes("Observacao")
                .build();

        RecebimentoResponse response = mapper.toRecebimentoResponse(recebimento);

        assertNotNull(response);

        assertEquals(recebimento.getId(), response.id());
        assertEquals(recebimento.getDataHoraRecebimento(), response.dataHoraRecebimento());
        assertEquals(recebimento.getPesoConferido(), response.pesoConferido());
        assertEquals(recebimento.getObservacoes(), response.observacoes());

    }

    @Test
    void atualizarRecebimento() {
        RecebimentoRequest request = RecebimentoRequest.builder()
                .dataHoraRecebimento(LocalDateTime.now())
                .observacoes("Observacao")
                .build();

        Recebimento recebimento = Recebimento.builder()
                .id(1L)
                .dataHoraRecebimento(LocalDateTime.now())
                .pesoConferido(null)
                .observacoes("Observacao")
                .build();

        mapper.atualizarRecebimento(request, recebimento);

        assertEquals(request.dataHoraRecebimento(), recebimento.getDataHoraRecebimento());
        assertEquals(request.observacoes(), recebimento.getObservacoes());
    }
}