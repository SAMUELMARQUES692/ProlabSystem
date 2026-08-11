package prolab.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import prolab.system.configuration.BaseIntegrationTest;
import prolab.system.entity.Agendamento;
import prolab.system.entity.Caminhao;
import prolab.system.entity.Cliente;
import prolab.system.entity.Recebimento;
import prolab.system.enums.StatusAgendamento;
import prolab.system.enums.TipoDeDestruicao;
import prolab.system.repository.AgendamentoRepository;
import prolab.system.repository.CaminhaoRepository;
import prolab.system.repository.ClienteRepository;
import prolab.system.repository.RecebimentoRepository;
import prolab.system.request.RecebimentoRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RecebimentoControllerTest extends BaseIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RecebimentoRepository recebimentoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository;


    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    @Test
    void cadastrar() throws Exception{
        Caminhao caminhao = caminhaoRepository.save(
                Caminhao.builder()
                        .placa("ABC1234")
                        .modelo("Modelo Teste")
                        .motorista("Motorista Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );


        RecebimentoRequest request = RecebimentoRequest.builder()
                .agendamentoId(1L)
                .placaCaminhao(caminhao.getPlaca())
                .modeloCaminhao(caminhao.getModelo())
                .motoristaCaminhao(caminhao.getMotorista())
                .dataHoraRecebimento(LocalDateTime.now())
                .pesoConferido(BigDecimal.TEN)
                .observacoes("Obs Teste")
                .build();

        mockMvc.perform(post("/api/recebimentos")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.agendamentoId").value(request.agendamentoId()))
                .andExpect(jsonPath("$.pesoConferido").value(request.pesoConferido()))
                .andExpect(jsonPath("$.observacoes").value(request.observacoes()));
    }

    @Test
    void atualizar() throws Exception {
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

        RecebimentoRequest request = RecebimentoRequest.builder()
                .agendamentoId(agendamento.getId())
                .placaCaminhao(caminhao.getPlaca())
                .modeloCaminhao(caminhao.getModelo())
                .motoristaCaminhao(caminhao.getMotorista())
                .dataHoraRecebimento(LocalDateTime.now())
                .pesoConferido(BigDecimal.TEN)
                .observacoes("Obs Teste")
                .build();

        mockMvc.perform(put("/api/recebimentos/{id}", recebimento.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recebimento.getId()))
                .andExpect(jsonPath("$.agendamentoId").value(request.agendamentoId()))
                .andExpect(jsonPath("$.pesoConferido").value(request.pesoConferido()))
                .andExpect(jsonPath("$.observacoes").value(request.observacoes()));
    }

    @Test
    void deletar() throws Exception{
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

        mockMvc.perform(delete("/api/recebimentos/{id}", recebimento.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recebimento)))
                .andExpect(status().isNoContent());
    }
}