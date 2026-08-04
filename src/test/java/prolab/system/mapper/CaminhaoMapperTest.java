package prolab.system.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import prolab.system.entity.Agendamento;
import prolab.system.entity.Caminhao;
import prolab.system.request.CaminhaoRequest;
import prolab.system.response.CaminhaoResponse;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CaminhaoMapperTest {

    private final CaminhaoMapper mapper = Mappers.getMapper(CaminhaoMapper.class);

    @Test
    void toCaminhao() {

        CaminhaoRequest request = CaminhaoRequest.builder()
                .placa("ABC-1234")
                .modelo("Fiat Fiorino")
                .motorista("Samuel Rodrigues Marques")
                .build();

        Caminhao caminhao = mapper.toCaminhao(request);

        assertNotNull(request);

        assertEquals(request.placa(), caminhao.getPlaca());
        assertEquals(request.modelo(), caminhao.getModelo());
        assertEquals(request.motorista(),caminhao.getMotorista());
    }

    @Test
    void toCaminhaoResponse() {

        Caminhao caminhao = Caminhao.builder()
                .id(1L)
                .placa("asdfasd")
                .modelo("Fiat Fiorino")
                .motorista("Vinicius Rodrigues Marques")
                .createdAt(LocalDateTime.now())
                .build();

        CaminhaoResponse response = mapper.toCaminhaoResponse(caminhao);

        assertNotNull(response);

        assertEquals(caminhao.getId(), response.id());
        assertEquals(caminhao.getPlaca(), response.placa());
        assertEquals(caminhao.getModelo(), response.modelo());
        assertEquals(caminhao.getMotorista(), response.motorista());
        assertEquals(caminhao.getCreatedAt(), response.createdAt());
    }

    @Test
    void atualizarCaminhao() {

        Caminhao caminhao = Caminhao.builder()
                .id(1L)
                .placa("asdfasd")
                .modelo("Fiat Fiorino")
                .motorista("Vinicius Rodrigues Marques")
                .createdAt(LocalDateTime.now())
                .build();


        CaminhaoRequest request = CaminhaoRequest.builder()
                .placa("ABC-1234")
                .modelo("Fiat Fiorino")
                .motorista("Samuel Rodrigues Marques")
                .build();

        mapper.atualizarCaminhao(request, caminhao);

        assertNotNull(request);

        assertEquals(request.placa(), caminhao.getPlaca());
        assertEquals(request.modelo(), caminhao.getModelo());
        assertEquals(request.motorista(),caminhao.getMotorista());


    }
}