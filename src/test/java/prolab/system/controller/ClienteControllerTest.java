package prolab.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import prolab.system.configuration.BaseIntegrationTest;
import prolab.system.entity.Cliente;
import prolab.system.repository.ClienteRepository;
import prolab.system.request.ClienteRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClienteControllerTest extends BaseIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void cadastrar() throws Exception{
        ClienteRequest request = ClienteRequest.builder()
                .razaoSocial("Cliente Teste")
                .cnpj("12345678910123")
                .contato("Contato Teste")
                .endereco("Endereco Teste")
                .build();

        mockMvc.perform(post("/api/clientes")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.razaoSocial").value(request.razaoSocial()))
                .andExpect(jsonPath("$.contato").value(request.contato()))
                .andExpect(jsonPath("$.endereco").value(request.endereco()));
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

        ClienteRequest request = ClienteRequest.builder()
                .razaoSocial("Cliente Teste")
                .cnpj("12345678910123")
                .contato("Contato Teste")
                .endereco("Endereco Teste")
                .build();

        mockMvc.perform(put("/api/clientes/{id}", cliente.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.razaoSocial").value(request.razaoSocial()))
                .andExpect(jsonPath("$.contato").value(request.contato()))
                .andExpect(jsonPath("$.endereco").value(request.endereco()));
    }

    @Test
    void deletar() throws Exception{
        Cliente cliente = clienteRepository.save(
                Cliente.builder()
                        .razaoSocial("Cliente Teste")
                        .cnpj("12345678910123")
                        .contato("Contato Teste")
                        .endereco("Endereco Teste")
                        .ativo(false)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId() throws Exception{
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

        mockMvc.perform(put("/api/clientes/{id}", cliente.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.razaoSocial").value(cliente.getRazaoSocial()))
                .andExpect(jsonPath("$.cnpj").value(cliente.getCnpj()))
                .andExpect(jsonPath("$.contato").value(cliente.getContato()))
                .andExpect(jsonPath("$.endereco").value(cliente.getEndereco()))
                .andExpect(jsonPath("$.ativo").value(cliente.getAtivo()));
    }

    @Test
    void buscarTodos() throws Exception{
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

        mockMvc.perform(get("/api/clientes")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(cliente.getId()))
                .andExpect(jsonPath("$[0].razaoSocial").value(cliente.getRazaoSocial()))
                .andExpect(jsonPath("$[0].cnpj").value(cliente.getCnpj()))
                .andExpect(jsonPath("$[0].contato").value(cliente.getContato()))
                .andExpect(jsonPath("$[0].endereco").value(cliente.getEndereco()))
                .andExpect(jsonPath("$[0].ativo").value(cliente.getAtivo()));
    }

    @Test
    void buscarPorRazaoSocial() throws Exception {
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

        mockMvc.perform(get("/api/clientes/buscar-razao")
                        .param("razaoSocial", cliente.getRazaoSocial())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.razaoSocial").value(cliente.getRazaoSocial()))
                .andExpect(jsonPath("$.cnpj").value(cliente.getCnpj()))
                .andExpect(jsonPath("$.contato").value(cliente.getContato()))
                .andExpect(jsonPath("$.endereco").value(cliente.getEndereco()))
                .andExpect(jsonPath("$.ativo").value(cliente.getAtivo()));
    }
}