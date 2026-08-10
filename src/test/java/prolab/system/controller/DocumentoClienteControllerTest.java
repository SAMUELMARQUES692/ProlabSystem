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
import prolab.system.enums.TipoDeDestruicao;
import prolab.system.enums.TipoDocumento;
import prolab.system.repository.*;
import prolab.system.request.DocumentoClienteRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DocumentoClienteControllerTest extends BaseIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RecebimentoRepository recebimentoRepository;

    @Autowired
    private DocumentoClienteRepository documentoClienteRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void cadastrar() throws Exception {
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

        DocumentoClienteRequest request = DocumentoClienteRequest.builder()
                .clienteId(cliente.getId())
                .recebimentoId(recebimento.getId())
                .tipo(TipoDocumento.DECLARACAO)
                .numero("numero Teste")
                .arquivoUrl("Arquivo Teste")
                .dataEmissao(LocalDate.now())
                .observacoes("Obs Teste")
                .build();

        mockMvc.perform(post("/api/documentos")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clienteId").value(request.clienteId()))
                .andExpect(jsonPath("$.recebimentoId").value(request.recebimentoId()))
                .andExpect(jsonPath("$.tipo").value(request.tipo().name()))
                .andExpect(jsonPath("$.numero").value(request.numero()))
                .andExpect(jsonPath("$.arquivoUrl").value(request.arquivoUrl()))
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

        DocumentoClienteRequest request = DocumentoClienteRequest.builder()
                .clienteId(cliente.getId())
                .recebimentoId(recebimento.getId())
                .tipo(TipoDocumento.DECLARACAO)
                .numero("numero Teste")
                .arquivoUrl("Arquivo Teste")
                .dataEmissao(LocalDate.now())
                .observacoes("Obs Teste")
                .build();

        DocumentoCliente documentoCliente = documentoClienteRepository.save(
                DocumentoCliente.builder()
                        .cliente(cliente)
                        .recebimento(recebimento)
                        .tipo(TipoDocumento.DECLARACAO)
                        .numero("numero Teste")
                        .arquivoUrl("Arquivo Teste")
                        .dataEmissao(LocalDate.now())
                        .observacoes("Obs Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(put("/api/documentos/{id}", documentoCliente.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value(request.clienteId()))
                .andExpect(jsonPath("$.recebimentoId").value(request.recebimentoId()))
                .andExpect(jsonPath("$.tipo").value(request.tipo().name()))
                .andExpect(jsonPath("$.numero").value(request.numero()))
                .andExpect(jsonPath("$.arquivoUrl").value(request.arquivoUrl()))
                .andExpect(jsonPath("$.observacoes").value(request.observacoes()));
    }

    @Test
    void deletar() throws Exception {
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

        DocumentoCliente documentoCliente = documentoClienteRepository.save(
                DocumentoCliente.builder()
                        .cliente(cliente)
                        .recebimento(recebimento)
                        .tipo(TipoDocumento.DECLARACAO)
                        .numero("numero Teste")
                        .arquivoUrl("Arquivo Teste")
                        .dataEmissao(LocalDate.now())
                        .observacoes("Obs Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(delete("/api/documentos/{id}", documentoCliente.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(documentoCliente)))
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

        DocumentoCliente documentoCliente = documentoClienteRepository.save(
                DocumentoCliente.builder()
                        .cliente(cliente)
                        .recebimento(recebimento)
                        .tipo(TipoDocumento.DECLARACAO)
                        .numero("numero Teste")
                        .arquivoUrl("Arquivo Teste")
                        .dataEmissao(LocalDate.now())
                        .observacoes("Obs Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(get("/api/documentos/{id}", documentoCliente.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(documentoCliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value(documentoCliente.getTipo().name()))
                .andExpect(jsonPath("$.numero").value(documentoCliente.getNumero()))
                .andExpect(jsonPath("$.arquivoUrl").value(documentoCliente.getArquivoUrl()))
                .andExpect(jsonPath("$.observacoes").value(documentoCliente.getObservacoes()));
    }
}