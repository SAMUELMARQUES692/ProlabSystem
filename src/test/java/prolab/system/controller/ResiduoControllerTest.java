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
import prolab.system.request.AtualizarStatusRequest;
import prolab.system.request.ResiduoRequest;
import prolab.system.response.ResiduoResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ResiduoControllerTest extends BaseIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RecebimentoRepository recebimentoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository;

    @Autowired
    private PosicaoEstoqueRepository posicaoEstoqueRepository;

    @Autowired
    private ResiduoRepository residuoRepository;


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

        PosicaoEstoque posicaoEstoque = posicaoEstoqueRepository.save(
                PosicaoEstoque.builder()
                        .codigo("Codigo Teste")
                        .capacidade(BigDecimal.TEN)
                        .status(StatusPosicao.DISPONIVEL)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        ResiduoRequest request = ResiduoRequest.builder()
                .recebimentoId(recebimento.getId())
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
                .posicaoId(posicaoEstoque.getId())
                .mtrVinculado("MTR Teste")
                .build();

        mockMvc.perform(post("/api/residuos")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.recebimentoId").value(request.recebimentoId()))
                .andExpect(jsonPath("$.tipoResiduo").value(request.tipoResiduo()))
                .andExpect(jsonPath("$.posicaoId").value(request.posicaoId()))
                .andExpect(jsonPath("$.mtrVinculado").value(request.mtrVinculado()));
    }

    @Test
    void atualizar() throws Exception{
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
                        .status(StatusPosicao.DISPONIVEL)
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

        ResiduoRequest request = ResiduoRequest.builder()
                .recebimentoId(recebimento.getId())
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
                .posicaoId(posicaoEstoque.getId())
                .mtrVinculado("MTR Teste")
                .build();

        mockMvc.perform(put("/api/residuos/{id}", residuo.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(residuo.getId()))
                .andExpect(jsonPath("$.recebimentoId").value(request.recebimentoId()))
                .andExpect(jsonPath("$.tipoResiduo").value(request.tipoResiduo()))
                .andExpect(jsonPath("$.posicaoId").value(request.posicaoId()))
                .andExpect(jsonPath("$.mtrVinculado").value(request.mtrVinculado()));
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

        PosicaoEstoque posicaoEstoque = posicaoEstoqueRepository.save(
                PosicaoEstoque.builder()
                        .codigo("Codigo Teste")
                        .capacidade(BigDecimal.TEN)
                        .status(StatusPosicao.DISPONIVEL)
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

        mockMvc.perform(delete("/api/residuos/{id}", residuo.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(residuo)))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId() throws Exception {
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
                        .status(StatusPosicao.DISPONIVEL)
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
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
                .posicaoId(posicaoEstoque.getId())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        mockMvc.perform(get("/api/residuos/{id}", residuo.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.recebimentoId").value(response.recebimentoId()))
                .andExpect(jsonPath("$.tipoResiduo").value(response.tipoResiduo()))
                .andExpect(jsonPath("$.posicaoId").value(response.posicaoId()))
                .andExpect(jsonPath("$.status").value(response.status().name()))
                .andExpect(jsonPath("$.mtrVinculado").value(response.mtrVinculado()));
    }

    @Test
    void atualizarStatus() throws Exception{
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
                        .status(StatusPosicao.DISPONIVEL)
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

        AtualizarStatusRequest atualizarStatusRequest = AtualizarStatusRequest.builder()
                .novoStatus(StatusResiduo.EM_TRATAMENTO)
                .build();

        mockMvc.perform(patch("/api/residuos/{id}/status", residuo.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizarStatusRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(residuo.getId()))
                .andExpect(jsonPath("$.status").value(atualizarStatusRequest.novoStatus().name()));
    }

    @Test
    void buscarPorTipoResiduo() throws Exception{
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
                        .status(StatusPosicao.DISPONIVEL)
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
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
                .posicaoId(posicaoEstoque.getId())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        mockMvc.perform(get("/api/residuos/{tipoResiduo}/tipo", residuo.getTipoResiduo())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.id()))
                .andExpect(jsonPath("$[0].recebimentoId").value(response.recebimentoId()))
                .andExpect(jsonPath("$[0].tipoResiduo").value(response.tipoResiduo()))
                .andExpect(jsonPath("$[0].posicaoId").value(response.posicaoId()))
                .andExpect(jsonPath("$[0].status").value(response.status().name()))
                .andExpect(jsonPath("$[0].mtrVinculado").value(response.mtrVinculado()));
    }

    @Test
    void buscarResiduoPorPosicao() throws Exception {
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
                        .status(StatusPosicao.DISPONIVEL)
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
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
                .posicaoId(posicaoEstoque.getId())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        mockMvc.perform(get("/api/residuos/{posicaoId}/posicao", residuo.getPosicaoEstoque().getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.id()))
                .andExpect(jsonPath("$[0].recebimentoId").value(response.recebimentoId()))
                .andExpect(jsonPath("$[0].tipoResiduo").value(response.tipoResiduo()))
                .andExpect(jsonPath("$[0].posicaoId").value(response.posicaoId()))
                .andExpect(jsonPath("$[0].status").value(response.status().name()))
                .andExpect(jsonPath("$[0].mtrVinculado").value(response.mtrVinculado()));
    }

    @Test
    void buscarPorStatusResiduo() throws Exception {
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
                        .status(StatusPosicao.DISPONIVEL)
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
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
                .posicaoId(posicaoEstoque.getId())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        mockMvc.perform(get("/api/residuos/status-residuo", residuo.getStatus())
                        .param("status", "ARMAZENADO")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.id()))
                .andExpect(jsonPath("$[0].recebimentoId").value(response.recebimentoId()))
                .andExpect(jsonPath("$[0].tipoResiduo").value(response.tipoResiduo()))
                .andExpect(jsonPath("$[0].posicaoId").value(response.posicaoId()))
                .andExpect(jsonPath("$[0].status").value(response.status().name()))
                .andExpect(jsonPath("$[0].mtrVinculado").value(response.mtrVinculado()));
    }
}