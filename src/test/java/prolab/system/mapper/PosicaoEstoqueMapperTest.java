package prolab.system.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import prolab.system.entity.PosicaoEstoque;
import prolab.system.enums.StatusPosicao;
import prolab.system.request.PosicaoEstoqueRequest;
import prolab.system.response.PosicaoEstoqueResponse;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PosicaoEstoqueMapperTest {

    private final PosicaoEstoqueMapper mapper = Mappers.getMapper(PosicaoEstoqueMapper.class);

    @Test
    void toPosicaoEstoque() {
        PosicaoEstoqueRequest request = PosicaoEstoqueRequest.builder()
                .codigo("SAM123")
                .capacidade(null)
                .status(StatusPosicao.OCUPADA)
                .build();

        PosicaoEstoque posicaoEstoque = mapper.toPosicaoEstoque(request);

        assertNotNull(posicaoEstoque);

        assertEquals(request.codigo(), posicaoEstoque.getCodigo());
        assertEquals(request.capacidade(), posicaoEstoque.getCapacidade());
        assertEquals(request.status(), posicaoEstoque.getStatus());
    }

    @Test
    void toPosicaoEstoqueResponse() {
        PosicaoEstoque posicaoEstoque = PosicaoEstoque.builder()
                .id(1L)
                .codigo("SAM123")
                .capacidade(null)
                .status(StatusPosicao.OCUPADA)
                .createdAt(LocalDateTime.now())
                .build();

        PosicaoEstoqueResponse response = mapper.toPosicaoEstoqueResponse(posicaoEstoque);

        assertNotNull(response);

        assertEquals(posicaoEstoque.getId(), response.id());
        assertEquals(posicaoEstoque.getCodigo(), response.codigo());
        assertEquals(posicaoEstoque.getCapacidade(), response.capacidade());
        assertEquals(StatusPosicao.OCUPADA.name(), response.status());
        assertEquals(posicaoEstoque.getCreatedAt(), response.createdAt());
    }

    @Test
    void atualizarPosicaoEstoque() {
        PosicaoEstoqueRequest request = PosicaoEstoqueRequest.builder()
                .codigo("SAM123")
                .capacidade(null)
                .status(StatusPosicao.OCUPADA)
                .build();

        PosicaoEstoque posicaoEstoque = PosicaoEstoque.builder()
                .id(1L)
                .codigo("SAM123")
                .capacidade(null)
                .status(StatusPosicao.OCUPADA)
                .createdAt(LocalDateTime.now())
                .build();

        mapper.atualizarPosicaoEstoque(request, posicaoEstoque);

        assertNotNull(posicaoEstoque);

        assertEquals(request.codigo(), posicaoEstoque.getCodigo());
        assertEquals(request.capacidade(), posicaoEstoque.getCapacidade());
        assertEquals(request.status(), posicaoEstoque.getStatus());
    }
}