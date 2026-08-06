package prolab.system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import prolab.system.entity.PosicaoEstoque;
import prolab.system.entity.Recebimento;
import prolab.system.entity.Residuo;
import prolab.system.enums.StatusPosicao;
import prolab.system.enums.StatusResiduo;
import prolab.system.mapper.PosicaoEstoqueMapper;
import prolab.system.mapper.ResiduoMapper;
import prolab.system.repository.PosicaoEstoqueRepository;
import prolab.system.repository.ResiduoRepository;
import prolab.system.request.PosicaoEstoqueRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PosicaoEstoqueServiceTest {

    @InjectMocks
    PosicaoEstoqueService posicaoEstoqueService;

    @Mock
    PosicaoEstoqueMapper posicaoEstoqueMapper;

    @Mock
    ResiduoMapper residuoMapper;

    @Mock
    PosicaoEstoqueRepository posicaoEstoqueRepository;

    @Mock
    ResiduoRepository residuoRepository;

    @Captor
    ArgumentCaptor<PosicaoEstoque> argumentCaptor;

    @Test
    void cadastrar() {
        PosicaoEstoque posicaoEstoque = PosicaoEstoque.builder()
                .id(1L)
                .codigo("Codigo Teste")
                .capacidade(null)
                .status(StatusPosicao.OCUPADA)
                .createdAt(LocalDateTime.now())
                .build();

        PosicaoEstoqueRequest request = PosicaoEstoqueRequest.builder()
                .codigo("Codigo Teste")
                .capacidade(null)
                .status(StatusPosicao.OCUPADA)
                .build();

        Mockito.when(posicaoEstoqueRepository.findByCodigo(request.codigo())).thenReturn(Optional.empty());
        Mockito.when(posicaoEstoqueMapper.toPosicaoEstoque(request)).thenReturn(posicaoEstoque);

        posicaoEstoqueService.cadastrar(request);

        Mockito.verify(posicaoEstoqueRepository).findByCodigo(request.codigo());
        Mockito.verify(posicaoEstoqueMapper).toPosicaoEstoque(request);
        Mockito.verify(posicaoEstoqueMapper).toPosicaoEstoqueResponse(Mockito.any());
        Mockito.verify(posicaoEstoqueRepository).save(argumentCaptor.capture());
    }

    @Test
    void atualizar() {
        PosicaoEstoque posicaoEstoque = PosicaoEstoque.builder()
                .id(1L)
                .codigo("Codigo Teste")
                .capacidade(null)
                .status(StatusPosicao.OCUPADA)
                .createdAt(LocalDateTime.now())
                .build();

        PosicaoEstoqueRequest request = PosicaoEstoqueRequest.builder()
                .codigo("Codigo Teste")
                .capacidade(null)
                .status(StatusPosicao.OCUPADA)
                .build();

        Mockito.when(posicaoEstoqueRepository.findById(posicaoEstoque.getId())).thenReturn(Optional.of(posicaoEstoque));

        posicaoEstoqueService.atualizar(posicaoEstoque.getId(), request);

        Mockito.verify(posicaoEstoqueRepository).findById(posicaoEstoque.getId());
        Mockito.verify(posicaoEstoqueMapper).atualizarPosicaoEstoque(request, posicaoEstoque);
        Mockito.verify(posicaoEstoqueRepository).save(argumentCaptor.capture());
        Mockito.verify(posicaoEstoqueMapper).toPosicaoEstoqueResponse(Mockito.any());
    }

    @Test
    void deletar() {
        PosicaoEstoque posicaoEstoque = PosicaoEstoque.builder()
                .id(1L)
                .codigo("Codigo Teste")
                .capacidade(null)
                .status(StatusPosicao.OCUPADA)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(posicaoEstoqueRepository.findById(posicaoEstoque.getId())).thenReturn(Optional.of(posicaoEstoque));

        posicaoEstoqueService.deletar(posicaoEstoque.getId());

        Mockito.verify(posicaoEstoqueRepository).findById(posicaoEstoque.getId());
        Mockito.verify(posicaoEstoqueRepository).deleteById(posicaoEstoque.getId());
    }

    @Test
    void buscarTodas() {
        PosicaoEstoque posicaoEstoque = PosicaoEstoque.builder()
                .id(1L)
                .codigo("Codigo Teste")
                .capacidade(null)
                .status(StatusPosicao.OCUPADA)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(posicaoEstoqueRepository.findAll()).thenReturn(List.of(posicaoEstoque));

        posicaoEstoqueService.buscarTodas();

        Mockito.verify(posicaoEstoqueRepository).findAll();
        Mockito.verify(posicaoEstoqueMapper).toPosicaoEstoqueResponse(Mockito.any());
    }

    @Test
    void buscarResiduoPorCodigo() {
        PosicaoEstoque posicaoEstoque = PosicaoEstoque.builder()
                .id(1L)
                .codigo("Codigo Teste")
                .capacidade(null)
                .status(StatusPosicao.OCUPADA)
                .createdAt(LocalDateTime.now())
                .build();

        Residuo residuo = Residuo.builder()
                .id(1L)
                .recebimento(Recebimento.builder().id(1L).build())
                .tipoResiduo("Tipo Teste")
                .quantidade(null)
                .posicaoEstoque(PosicaoEstoque.builder().id(1L).build())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("mtr Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(posicaoEstoqueRepository.findByCodigo(posicaoEstoque.getCodigo())).thenReturn(Optional.of(posicaoEstoque));
        Mockito.when(residuoRepository.findByPosicaoEstoqueCodigo(posicaoEstoque.getCodigo())).thenReturn(List.of(residuo));

        posicaoEstoqueService.buscarResiduoPorCodigo(posicaoEstoque.getCodigo());

        Mockito.verify(posicaoEstoqueRepository).findByCodigo(posicaoEstoque.getCodigo());
        Mockito.verify(residuoRepository).findByPosicaoEstoqueCodigo(posicaoEstoque.getCodigo());
        Mockito.verify(residuoMapper).toResiduoResponse(Mockito.any());
    }

    @Test
    void buscarPosicaoPorStatus() {
        PosicaoEstoque posicaoEstoque = PosicaoEstoque.builder()
                .id(1L)
                .codigo("Codigo Teste")
                .capacidade(null)
                .status(StatusPosicao.OCUPADA)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(posicaoEstoqueRepository.findByStatus(posicaoEstoque.getStatus())).thenReturn(List.of(posicaoEstoque));

        posicaoEstoqueService.buscarPosicaoPorStatus(posicaoEstoque.getStatus());

        Mockito.verify(posicaoEstoqueRepository).findByStatus(posicaoEstoque.getStatus());
        Mockito.verify(posicaoEstoqueMapper).toPosicaoEstoqueResponse(Mockito.any());

    }
}