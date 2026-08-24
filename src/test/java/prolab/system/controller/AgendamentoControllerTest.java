package prolab.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import prolab.system.configuration.BaseIntegrationTest;
import prolab.system.entity.Agendamento;
import prolab.system.entity.Cliente;
import prolab.system.enums.StatusAgendamento;
import prolab.system.enums.TipoDeDestruicao;
import prolab.system.repository.AgendamentoRepository;
import prolab.system.repository.ClienteRepository;
import prolab.system.request.AgendamentoRequest;
import prolab.system.request.AtualizarAgendamentoRequest;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AgendamentoControllerTest extends BaseIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

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

        AgendamentoRequest request = AgendamentoRequest.builder()
                .clienteId(cliente.getId())
                .tipoResiduo("Papelão")
                .tipoDeDestruicao(TipoDeDestruicao.LOGISTICA_REVERSA)
                .quantidadePaletes(500)
                .dataHoraPrevista(LocalDateTime.now().plusDays(1))
                .build();

        mockMvc.perform(post("/api/agendamentos")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clienteId").value(request.clienteId()))
                .andExpect(jsonPath("$.tipoResiduo").value(request.tipoResiduo()))
                .andExpect(jsonPath("$.tipoDeDestruicao").value(request.tipoDeDestruicao().name()))
                .andExpect(jsonPath("$.quantidadePaletes").value(request.quantidadePaletes()));
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


        AgendamentoRequest request = AgendamentoRequest.builder()
                .clienteId(cliente.getId())
                .tipoResiduo("Papelão")
                .tipoDeDestruicao(TipoDeDestruicao.LOGISTICA_REVERSA)
                .quantidadePaletes(500)
                .dataHoraPrevista(LocalDateTime.now().plusDays(1))
                .build();

        mockMvc.perform(put("/api/agendamentos/{id}", agendamento.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType( MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value(request.clienteId()))
                .andExpect(jsonPath("$.tipoResiduo").value(request.tipoResiduo()))
                .andExpect(jsonPath("$.tipoDeDestruicao").value(request.tipoDeDestruicao().name()))
                .andExpect(jsonPath("$.quantidadePaletes").value(request.quantidadePaletes()));
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

        mockMvc.perform(delete("/api/agendamentos/{id}", agendamento.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agendamento)))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorclienteId() throws Exception{
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

        mockMvc.perform(get("/api/agendamentos/cliente/{clienteId}", cliente.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN"))))
                        .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(agendamento.getId()))
                .andExpect(jsonPath("$[0].tipoResiduo").value(agendamento.getTipoResiduo()))
                .andExpect(jsonPath("$[0].tipoDeDestruicao").value(agendamento.getTipoDeDestruicao().name()))
                .andExpect(jsonPath("$[0].quantidadePaletes").value(agendamento.getQuantidadePaletes()))
                .andExpect(jsonPath("$[0].status").value(agendamento.getStatus().name()));
    }

    @Test
    void buscarPorStatus() throws Exception{
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

        mockMvc.perform(get("/api/agendamentos/status/{status}", agendamento.getStatus())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(agendamento.getId()))
                .andExpect(jsonPath("$[0].tipoResiduo").value(agendamento.getTipoResiduo()))
                .andExpect(jsonPath("$[0].tipoDeDestruicao").value(agendamento.getTipoDeDestruicao().name()))
                .andExpect(jsonPath("$[0].quantidadePaletes").value(agendamento.getQuantidadePaletes()))
                .andExpect(jsonPath("$[0].status").value(agendamento.getStatus().name()));
    }

    @Test
    void buscarPorTipoDeDestruicao() throws Exception{
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

        mockMvc.perform(get("/api/agendamentos/buscar-tipo")
                        .param("tipo", "DESTRUICAO_DIRETA")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(agendamento.getId()))
                .andExpect(jsonPath("$[0].tipoResiduo").value(agendamento.getTipoResiduo()))
                .andExpect(jsonPath("$[0].tipoDeDestruicao").value(agendamento.getTipoDeDestruicao().name()))
                .andExpect(jsonPath("$[0].quantidadePaletes").value(agendamento.getQuantidadePaletes()))
                .andExpect(jsonPath("$[0].status").value(agendamento.getStatus().name()));
    }

    @Test
    void atualizarAgendamento() throws Exception {
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

        AtualizarAgendamentoRequest request = AtualizarAgendamentoRequest.builder()
                .novoAgendamento(agendamento.getStatus())
                .build();

        mockMvc.perform(put("/api/agendamentos/atualizar-agendamento/{id}", agendamento.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                .contentType( MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(agendamento.getId()));
    }

}