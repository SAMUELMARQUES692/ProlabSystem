package prolab.system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import prolab.system.entity.Caminhao;
import prolab.system.mapper.CaminhaoMapper;
import prolab.system.repository.CaminhaoRepository;
import prolab.system.request.CaminhaoRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CaminhaoServiceTest {

    @InjectMocks
    CaminhaoService caminhaoService;

    @Mock
    CaminhaoRepository caminhaoRepository;

    @Mock
    CaminhaoMapper caminhaoMapper;

    @Captor
    ArgumentCaptor<Caminhao> argumentCaptor;

    @Test
    void cadastrar() {

        Caminhao caminhao = Caminhao.builder()
                .id(1L)
                .placa("ABC1234")
                .modelo("Modelo Teste")
                .motorista("Motorista Teste")
                .createdAt(LocalDateTime.now())
                .build();

        CaminhaoRequest request = CaminhaoRequest.builder()
                .placa("ABC1234")
                .modelo("Modelo Teste")
                .motorista("Motorista Teste")
                .build();

        Mockito.when(caminhaoRepository.findByPlaca(request.placa())).thenReturn(Optional.empty());
        Mockito.when(caminhaoMapper.toCaminhao(request)).thenReturn(caminhao);

        caminhaoService.cadastrar(request);

        Mockito.verify(caminhaoRepository).save(Mockito.any());
        Mockito.verify(caminhaoMapper).toCaminhao(Mockito.any());
        Mockito.verify(caminhaoMapper).toCaminhaoResponse(Mockito.any());
        Mockito.verify(caminhaoRepository).save(argumentCaptor.capture());
    }

    @Test
    void atualizar() {

        Caminhao caminhao = Caminhao.builder()
                .id(1L)
                .placa("ABC1234")
                .modelo("Modelo Teste")
                .motorista("Motorista Teste")
                .createdAt(LocalDateTime.now())
                .build();

        CaminhaoRequest request = CaminhaoRequest.builder()
                .placa("ABC1234")
                .modelo("Modelo Teste")
                .motorista("Motorista Teste")
                .build();

        Mockito.when(caminhaoRepository.findById(caminhao.getId())).thenReturn(Optional.of(caminhao));

        caminhaoService.atualizar(caminhao.getId(), request);

        Mockito.verify(caminhaoRepository).findById(caminhao.getId());
        Mockito.verify(caminhaoRepository).save(Mockito.any());
        Mockito.verify(caminhaoMapper).atualizarCaminhao(request, caminhao);
        Mockito.verify(caminhaoMapper).toCaminhaoResponse(Mockito.any());
        Mockito.verify(caminhaoRepository).save(argumentCaptor.capture());
    }

    @Test
    void deletar() {
        Caminhao caminhao = Caminhao.builder()
                .id(1L)
                .placa("ABC1234")
                .modelo("Modelo Teste")
                .motorista("Motorista Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(caminhaoRepository.findById(caminhao.getId())).thenReturn(Optional.of(caminhao));

        caminhaoService.deletar(caminhao.getId());

        Mockito.verify(caminhaoRepository).findById(caminhao.getId());
        Mockito.verify(caminhaoRepository).deleteById(caminhao.getId());
    }

    @Test
    void buscarTodos() {
        Caminhao caminhao = Caminhao.builder()
                .id(1L)
                .placa("ABC1234")
                .modelo("Modelo Teste")
                .motorista("Motorista Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(caminhaoRepository.findAll()).thenReturn(List.of(caminhao));

        caminhaoService.buscarTodos();

        Mockito.verify(caminhaoRepository).findAll();
        Mockito.verify(caminhaoMapper).toCaminhaoResponse(Mockito.any());
    }
}