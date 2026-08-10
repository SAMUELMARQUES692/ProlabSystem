package prolab.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import prolab.system.configuration.BaseIntegrationTest;
import prolab.system.entity.Caminhao;
import prolab.system.repository.CaminhaoRepository;
import prolab.system.request.CaminhaoRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CaminhaoControllerTest extends BaseIntegrationTest {

    @Autowired
    private CaminhaoRepository caminhaoRepository;


    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void cadastrar() throws Exception {
        CaminhaoRequest request = CaminhaoRequest.builder()
                .placa("ABC1234")
                .modelo("Modelo Teste")
                .motorista("Motorista Teste")
                .build();

        mockMvc.perform(post("/api/caminhoes")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.modelo").value(request.modelo()))
                .andExpect(jsonPath("$.motorista").value(request.motorista()));
    }

    @Test
    void atualizar() throws Exception {
        Caminhao caminhao = caminhaoRepository.save(
          Caminhao.builder()
                  .placa("ABC1234")
                  .modelo("Modelo Teste")
                  .motorista("Motorista Teste")
                  .createdAt(LocalDateTime.now())
                  .build()
        );

        CaminhaoRequest request = CaminhaoRequest.builder()
                .placa("ABC1234")
                .modelo("Modelo Teste")
                .motorista("Motorista Teste")
                .build();

        mockMvc.perform(put("/api/caminhoes/{id}", caminhao.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType( MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value(request.modelo()))
                .andExpect(jsonPath("$.motorista").value(request.motorista()));
    }

    @Test
    void deletar() throws Exception {
        Caminhao caminhao = caminhaoRepository.save(
                Caminhao.builder()
                        .placa("ABC1234")
                        .modelo("Modelo Teste")
                        .motorista("Motorista Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(delete("/api/caminhoes/{id}", caminhao.getId())
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .contentType( MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(caminhao)))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarTodos() throws Exception {
        Caminhao caminhao = caminhaoRepository.save(
                Caminhao.builder()
                        .placa("ABC1234")
                        .modelo("Modelo Teste")
                        .motorista("Motorista Teste")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        mockMvc.perform(get("/api/caminhoes")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_ADMIN"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(caminhao.getId()))
                .andExpect(jsonPath("$[0].placa").value(caminhao.getPlaca()))
                .andExpect(jsonPath("$[0].modelo").value(caminhao.getModelo()))
                .andExpect(jsonPath("$[0].motorista").value(caminhao.getMotorista()));
    }
}