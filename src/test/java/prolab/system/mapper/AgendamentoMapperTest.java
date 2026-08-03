package prolab.system.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import prolab.system.entity.Agendamento;
import prolab.system.enums.StatusAgendamento;
import prolab.system.enums.TipoDeDestruicao;
import prolab.system.request.AgendamentoRequest;
import prolab.system.response.AgendamentoResponse;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AgendamentoMapperTest {

    private final AgendamentoMapper mapper = Mappers.getMapper(AgendamentoMapper.class);

    @Test
    void toAgendamento() {

        // Arrange - Given : Você prepara todo o objeto que sera usado para o teste.
        AgendamentoRequest request = AgendamentoRequest.builder()
                .clienteId(1L)
                .tipoResiduo("Papelão")
                .tipoDeDestruicao(TipoDeDestruicao.LOGISTICA_REVERSA)
                .quantidadePaletes(500)
                .dataHoraPrevista(LocalDateTime.now())
                .build();

        // Action - When : Aqui o metodo sera executado
        Agendamento agendamento = mapper.toAgendamento(request);

        // Assertions - Then : Aqui validamos se o metodo esta correto
        assertNotNull(agendamento); // -> este metodo apenas ve se o Objeto esta NULL

        // Este metodo verifica se o campo da Request é igual ao campo do agendamento, evitando que o mapper esteja errado.
        assertEquals(request.tipoResiduo(), agendamento.getTipoResiduo());
        assertEquals(request.tipoDeDestruicao(), agendamento.getTipoDeDestruicao());
        assertEquals(request.quantidadePaletes(), agendamento.getQuantidadePaletes());
        assertEquals(request.dataHoraPrevista(), agendamento.getDataHoraPrevista());
    }

    @Test
    void toAgendamentoResponse() {
        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .tipoResiduo("Plastico")
                .tipoDeDestruicao(TipoDeDestruicao.DESTRUICAO_DIRETA)
                .quantidadePaletes(150)
                .dataHoraPrevista(LocalDateTime.now())
                .status(StatusAgendamento.AGENDADO)
                .createdAt(LocalDateTime.now())
                .build();


        AgendamentoResponse agendamentoResponse = mapper.toAgendamentoResponse(agendamento);

        assertNotNull(agendamentoResponse);

        assertEquals(agendamento.getId(), agendamentoResponse.id());
        assertEquals(agendamento.getTipoResiduo(), agendamentoResponse.tipoResiduo());
        assertEquals(TipoDeDestruicao.DESTRUICAO_DIRETA.name(), agendamentoResponse.tipoDeDestruicao());
        assertEquals(agendamento.getQuantidadePaletes(),agendamentoResponse.quantidadePaletes());
        assertEquals(agendamento.getDataHoraPrevista(), agendamentoResponse.dataHoraPrevista());
        assertEquals(StatusAgendamento.AGENDADO.name(), agendamentoResponse.status());
        assertEquals(agendamento.getCreatedAt(), agendamentoResponse.createdAt());
    }

    @Test
    void atualizarAgendamento() {

        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .tipoResiduo("Plastico")
                .tipoDeDestruicao(TipoDeDestruicao.DESTRUICAO_DIRETA)
                .quantidadePaletes(150)
                .dataHoraPrevista(LocalDateTime.now())
                .status(StatusAgendamento.AGENDADO)
                .createdAt(LocalDateTime.now())
                .build();

        AgendamentoRequest request = AgendamentoRequest.builder()
                .tipoResiduo("Plastico")
                .tipoDeDestruicao(TipoDeDestruicao.DESTRUICAO_DIRETA)
                .quantidadePaletes(150)
                .dataHoraPrevista(LocalDateTime.now())
                .build();


        mapper.atualizarAgendamento(request, agendamento);

        assertNotNull(agendamento);
        assertNotNull(request);

        assertEquals(request.tipoResiduo(), agendamento.getTipoResiduo());
        assertEquals(request.tipoDeDestruicao(), agendamento.getTipoDeDestruicao());
        assertEquals(request.quantidadePaletes(), agendamento.getQuantidadePaletes());
        assertEquals(request.dataHoraPrevista(), agendamento.getDataHoraPrevista());
        
    }
}