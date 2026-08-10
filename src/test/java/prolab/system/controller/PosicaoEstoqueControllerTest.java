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
import prolab.system.enums.StatusAgendamento;
import prolab.system.enums.StatusPosicao;
import prolab.system.enums.StatusResiduo;
import prolab.system.enums.TipoDeDestruicao;
import prolab.system.repository.*;
import prolab.system.request.PosicaoEstoqueRequest;
import prolab.system.response.ResiduoResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PosicaoEstoqueControllerTest extends BaseIntegrationTest {

    @Autowired
    private PosicaoEstoqueRepository posicaoEstoqueRepository;

    @Autowired
    private ResiduoRepository residuoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RecebimentoRepository recebimentoRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;


    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void cadastrar() throws Exception{
        PosicaoEstoqueRequest request = PosicaoEstoqueRequest.builder()
                .codigo("Codigo Teste")
                .capacidade(BigDecimal.TEN)
                .status(StatusPosicao.DISPONIVEL)
                .build();

        mockMvc.perform(post("/api/posicoes")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigo").value(request.codigo()))
                .andExpect(jsonPath("$.capacidade").value(request.capacidade()))
                .andExpect(jsonPath("$.status").value(request.status().name()));
    }

    @Test
    void atualizar() throws Exception{
        PosicaoEstoque posicaoEstoque = posicaoEstoqueRepository.save(
                PosicaoEstoque.builder()
                        .codigo("Codigo Teste")
                        .capacidade(BigDecimal.TEN)
                        .status(StatusPosicao.OCUPADA)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        PosicaoEstoqueRequest request = PosicaoEstoqueRequest.builder()
                .codigo("Codigo Teste")
                .capacidade(BigDecimal.TEN)
                .status(StatusPosicao.DISPONIVEL)
                .build();

        mockMvc.perform(put("/api/posicoes/{id}" , posicaoEstoque.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(posicaoEstoque.getId()))
                .andExpect(jsonPath("$.codigo").value(request.codigo()))
                .andExpect(jsonPath("$.capacidade").value(request.capacidade()))
                .andExpect(jsonPath("$.status").value(request.status().name()));
    }

    @Test
    void deletar() throws Exception{
        PosicaoEstoque posicaoEstoque = posicaoEstoqueRepository.save(
                PosicaoEstoque.builder()
                        .codigo("Codigo Teste")
                        .capacidade(BigDecimal.TEN)
                        .status(StatusPosicao.OCUPADA)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(delete("/api/posicoes/{id}" , posicaoEstoque.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(posicaoEstoque)))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarTodas() throws Exception{
        PosicaoEstoque posicaoEstoque = posicaoEstoqueRepository.save(
                PosicaoEstoque.builder()
                        .codigo("Codigo Teste")
                        .capacidade(BigDecimal.TEN)
                        .status(StatusPosicao.OCUPADA)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(get("/api/posicoes")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(posicaoEstoque)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(posicaoEstoque.getId()))
                .andExpect(jsonPath("$[0].codigo").value(posicaoEstoque.getCodigo()))
                .andExpect(jsonPath("$[0].capacidade").value(posicaoEstoque.getCapacidade()))
                .andExpect(jsonPath("$[0].status").value(posicaoEstoque.getStatus().name()));
    }

    @Test
    void buscarPorCodigo() throws Exception {
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

        PosicaoEstoque posicaoEstoque = posicaoEstoqueRepository.save(
                PosicaoEstoque.builder()
                        .codigo("Codigo Teste")
                        .capacidade(BigDecimal.TEN)
                        .status(StatusPosicao.OCUPADA)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Residuo residuo = residuoRepository.save(
                Residuo.builder()
                        .recebimento(recebimento)
                        .tipoResiduo("Tipo Teste")
                        .quantidade(BigDecimal.TEN)
                        .posicaoEstoque(posicaoEstoque)
                        .status(StatusResiduo.ARMAZENADO)
                        .mtrVinculado("MTR Teste")
                        .dataDestinacao(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        ResiduoResponse response = ResiduoResponse.builder()
                .id(residuo.getId())
                .recebimentoId(recebimento.getId())
                .tipoResiduo(residuo.getTipoResiduo())
                .quantidade(residuo.getQuantidade())
                .posicaoId(posicaoEstoque.getId())
                .status(residuo.getStatus())
                .mtrVinculado(residuo.getMtrVinculado())
                .dataDestinacao(residuo.getDataDestinacao())
                .createdAt(residuo.getCreatedAt())
                .build();



        mockMvc.perform(get("/api/posicoes/{codigo}/residuos", posicaoEstoque.getCodigo())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(residuo.getId()))
                .andExpect(jsonPath("$[0].recebimentoId").value(response.recebimentoId()))
                .andExpect(jsonPath("$[0].tipoResiduo").value(response.tipoResiduo()))
                .andExpect(jsonPath("$[0].quantidade").value(response.quantidade()))
                .andExpect(jsonPath("$[0].posicaoId").value(response.posicaoId()))
                .andExpect(jsonPath("$[0].status").value(response.status().name()))
                .andExpect(jsonPath("$[0].mtrVinculado").value(response.mtrVinculado()));

    }

    @Test
    void buscarPosicaoPorStatus() throws Exception{
        PosicaoEstoque posicaoEstoque = posicaoEstoqueRepository.save(
                PosicaoEstoque.builder()
                        .codigo("Codigo Teste")
                        .capacidade(BigDecimal.TEN)
                        .status(StatusPosicao.OCUPADA)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(get("/api/posicoes/posicao-status", posicaoEstoque.getStatus())
                        .param("status", "OCUPADA")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(posicaoEstoque)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(posicaoEstoque.getId()))
                .andExpect(jsonPath("$[0].codigo").value(posicaoEstoque.getCodigo()))
                .andExpect(jsonPath("$[0].capacidade").value(posicaoEstoque.getCapacidade()))
                .andExpect(jsonPath("$[0].status").value(posicaoEstoque.getStatus().name()));

    }
}