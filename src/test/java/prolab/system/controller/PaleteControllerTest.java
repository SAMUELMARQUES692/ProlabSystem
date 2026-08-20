package prolab.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import prolab.system.configuration.BaseIntegrationTest;
import prolab.system.entity.*;
import prolab.system.enums.EstadoFisico;
import prolab.system.enums.StatusAgendamento;
import prolab.system.enums.TipoDeDestruicao;
import prolab.system.enums.TipoResiduo;
import prolab.system.repository.*;
import prolab.system.request.PaleteRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaleteControllerTest extends BaseIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RecebimentoRepository recebimentoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository;

    @Autowired
    private PaleteRepository paleteRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void cadastrar() throws Exception{
        Cliente cliente = clienteRepository.save(
                Cliente.builder()
                        .razaoSocial("Cliente Teste")
                        .cnpj("12345678910123")
                        .contato("Contato Teste")
                        .endereco("Endereco Teste")
                        .ativo(true)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Agendamento agendamento = agendamentoRepository.save(
                Agendamento.builder()
                        .cliente(cliente)
                        .tipoResiduo("Residuo Teste")
                        .tipoDeDestruicao(TipoDeDestruicao.DESTRUICAO_DIRETA)
                        .quantidadePaletes(10)
                        .dataHoraPrevista(LocalDateTime.now())
                        .status(StatusAgendamento.AGENDADO)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Caminhao caminhao = caminhaoRepository.save(
                Caminhao.builder()
                        .placa("ABC1234")
                        .modelo("Modelo Teste")
                        .motorista("Motorista Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Recebimento recebimento = recebimentoRepository.save(
                Recebimento.builder()
                        .agendamento(agendamento)
                        .cliente(cliente)
                        .caminhao(caminhao)
                        .prime("Prime Test")
                        .dataHoraRecebimento(LocalDateTime.now())
                        .pesoConferido(BigDecimal.TEN)
                        .observacoes("Observação Test")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        PaleteRequest request = PaleteRequest.builder()
                .recebimentoId(1L)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.TEN)
                .estadoFisico(EstadoFisico.SOLIDO)
                .build();

        mockMvc.perform(post("/api/paletes")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.recebimentoId").value(request.recebimentoId()))
                .andExpect(jsonPath("$.tipo").value(request.tipo().name()))
                .andExpect(jsonPath("$.peso").value(request.peso()))
                .andExpect(jsonPath("$.estadoFisico").value(request.estadoFisico().name()));
    }
}