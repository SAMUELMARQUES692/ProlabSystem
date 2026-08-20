package prolab.system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import prolab.system.entity.*;
import prolab.system.enums.EstadoFisico;
import prolab.system.enums.StatusPosicao;
import prolab.system.enums.StatusResiduo;
import prolab.system.enums.TipoResiduo;
import prolab.system.exception.TransicaoStatusInvalidaException;
import prolab.system.mapper.ResiduoMapper;
import prolab.system.repository.PaleteRepository;
import prolab.system.repository.PosicaoEstoqueRepository;
import prolab.system.repository.RecebimentoRepository;
import prolab.system.repository.ResiduoRepository;
import prolab.system.request.ResiduoRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ResiduoServiceTest {

    @InjectMocks
    ResiduoService residuoService;

    @Mock
    ResiduoMapper residuoMapper;

    @Mock
    ResiduoRepository residuoRepository;

    @Mock
    RecebimentoRepository recebimentoRepository;

    @Mock
    PosicaoEstoqueRepository posicaoEstoqueRepository;

    @Mock
    PaleteRepository paleteRepository;

    @Captor
    ArgumentCaptor<Residuo> argumentCaptor;

    @Test
    void cadastrar() {
        ResiduoRequest request = ResiduoRequest.builder()
                .paleteId(1L)
                .posicaoId(1L)
                .mtrVinculado("MTR Teste")
                .build();

        Recebimento recebimento = Recebimento.builder()
                .id(1L)
                .agendamento(Agendamento.builder().id(1L).build())
                .cliente(Cliente.builder().id(1L).build())
                .caminhao(Caminhao.builder().id(1L).build())
                .prime("Prime Teste")
                .dataHoraRecebimento(LocalDateTime.now())
                .pesoConferido(BigDecimal.TEN)
                .observacoes("Observacao Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Palete palete = Palete.builder()
                .id(1L)
                .ticket("Ticket Teste")
                .numeroPalete(2)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.ONE)
                .estadoFisico(EstadoFisico.LIQUIDO)
                .recebimento(recebimento)
                .createdAt(LocalDateTime.now())
                .build();

        PosicaoEstoque posicaoEstoque = PosicaoEstoque.builder()
                .id(1L)
                .codigo("Codigo Teste")
                .capacidade(BigDecimal.TEN)
                .status(StatusPosicao.OCUPADA)
                .createdAt(LocalDateTime.now())
                .build();

        Residuo residuo = Residuo.builder()
                .id(1L)
                .palete(palete)
                .posicaoEstoque(posicaoEstoque)
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(paleteRepository.findById(request.paleteId())).thenReturn(Optional.of(palete));
        Mockito.when(posicaoEstoqueRepository.findById(request.posicaoId())).thenReturn(Optional.of(posicaoEstoque));
        Mockito.when(residuoRepository.somarPesoPorPosicao(request.posicaoId())).thenReturn(BigDecimal.ONE); // ou qualquer valor que faça sentido pro cenário
        Mockito.when(residuoMapper.toResiduo(request)).thenReturn(residuo);
        Mockito.when(residuoRepository.save(residuo)).thenReturn(residuo);

        residuoService.cadastrar(request);

        Mockito.verify(paleteRepository).findById(request.paleteId());
        Mockito.verify(posicaoEstoqueRepository).findById(request.posicaoId());
        Mockito.verify(residuoRepository).somarPesoPorPosicao(request.posicaoId());
        Mockito.verify(residuoMapper).toResiduo(request);
        Mockito.verify(residuoRepository).save(argumentCaptor.capture());
        Mockito.verify(residuoMapper).toResiduoResponse(Mockito.any());
    }

    @Test
    void atualizar() {
        Residuo residuo = Residuo.builder()
                .id(1L)
                .palete(Palete.builder().id(1L).build())
                .posicaoEstoque(PosicaoEstoque.builder().id(1L).build())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        ResiduoRequest request = ResiduoRequest.builder()
                .paleteId(1L)
                .posicaoId(1L)
                .mtrVinculado("MTR Teste")
                .build();

        Mockito.when(residuoRepository.findById(residuo.getId())).thenReturn(Optional.of(residuo));

        residuoService.atualizar(residuo.getId(), request);

        Mockito.verify(residuoRepository).findById(residuo.getId());
        Mockito.verify(residuoMapper).atualizarResiduo(request, residuo);
        Mockito.verify(residuoRepository).save(argumentCaptor.capture());
        Mockito.verify(residuoMapper).toResiduoResponse(Mockito.any());
    }

    @Test
    void deletar() {
        Residuo residuo = Residuo.builder()
                .id(1L)
                .palete(Palete.builder().id(1L).build())
                .posicaoEstoque(PosicaoEstoque.builder().id(1L).build())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(residuoRepository.findById(residuo.getId())).thenReturn(Optional.of(residuo));

        residuoService.deletar(residuo.getId());

        Mockito.verify(residuoRepository).findById(residuo.getId());
        Mockito.verify(residuoRepository).deleteById(residuo.getId());
    }

    @Test
    void buscarPorId() {
        Residuo residuo = Residuo.builder()
                .id(1L)
                .palete(Palete.builder().id(1L).build())
                .posicaoEstoque(PosicaoEstoque.builder().id(1L).build())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(residuoRepository.findById(residuo.getId())).thenReturn(Optional.of(residuo));

        residuoService.buscarPorId(residuo.getId());

        Mockito.verify(residuoRepository).findById(residuo.getId());
        Mockito.verify(residuoMapper).toResiduoResponse(residuo);
    }

    @Test
    void avancarStatus() {
        Residuo residuo = Residuo.builder()
                .id(1L)
                .palete(Palete.builder().id(1L).build())
                .posicaoEstoque(PosicaoEstoque.builder().id(1L).build())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(residuoRepository.findById(residuo.getId())).thenReturn(Optional.of(residuo));

        residuoService.avancarStatus(residuo.getId(), StatusResiduo.EM_TRATAMENTO);

        Mockito.verify(residuoRepository).findById(residuo.getId());
        Mockito.verify(residuoRepository).save(argumentCaptor.capture());
        Mockito.verify(residuoMapper).toResiduoResponse(Mockito.any());
    }

    @Test
    void avancarStatus_transicaoInvalida_deveLancarExcecao() {
        Residuo residuo = Residuo.builder()
                .id(1L)
                .status(StatusResiduo.ARMAZENADO)
                .build();

        Mockito.when(residuoRepository.findById(residuo.getId())).thenReturn(Optional.of(residuo));

        assertThrows(TransicaoStatusInvalidaException.class, () ->
                residuoService.avancarStatus(residuo.getId(), StatusResiduo.DESTRUIDO)
        );

        Mockito.verify(residuoRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void buscarPorTipoResiduo() {
        Palete palete = Palete.builder()
                .id(1L)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .build();

        Residuo residuo = Residuo.builder()
                .id(1L)
                .palete(palete)
                .posicaoEstoque(PosicaoEstoque.builder().id(1L).build())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(residuoRepository.findByPaleteTipo(residuo.getPalete().getTipo())).thenReturn(List.of(residuo));

        residuoService.buscarPorTipoResiduo(palete.getTipo());

        Mockito.verify(residuoRepository).findByPaleteTipo(residuo.getPalete().getTipo());
        Mockito.verify(residuoMapper).toResiduoResponse(residuo);
    }

    @Test
    void buscarPorPosicao() {
        Residuo residuo = Residuo.builder()
                .id(1L)
                .palete(Palete.builder().id(1L).build())
                .posicaoEstoque(PosicaoEstoque.builder().id(1L).build())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        PosicaoEstoque posicaoEstoque = PosicaoEstoque.builder()
                .id(1L)
                .codigo("Codigo Teste")
                .capacidade(BigDecimal.TEN)
                .status(StatusPosicao.OCUPADA)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(posicaoEstoqueRepository.findById(posicaoEstoque.getId())).thenReturn(Optional.of(posicaoEstoque));
        Mockito.when(residuoRepository.findByPosicaoEstoqueId(posicaoEstoque.getId())).thenReturn(List.of(residuo));

        residuoService.buscarPorPosicao(posicaoEstoque.getId());

        Mockito.verify(posicaoEstoqueRepository).findById(posicaoEstoque.getId());
        Mockito.verify(residuoRepository).findByPosicaoEstoqueId(posicaoEstoque.getId());
        Mockito.verify(residuoMapper).toResiduoResponse(residuo);
    }

    @Test
    void buscarPorStatusResiduo() {
        Residuo residuo = Residuo.builder()
                .id(1L)
                .palete(Palete.builder().id(1L).build())
                .posicaoEstoque(PosicaoEstoque.builder().id(1L).build())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(residuoRepository.findByStatus(StatusResiduo.ARMAZENADO)).thenReturn(List.of(residuo));

        residuoService.buscarPorStatusResiduo(StatusResiduo.ARMAZENADO);

        Mockito.verify(residuoRepository).findByStatus(StatusResiduo.ARMAZENADO);
        Mockito.verify(residuoMapper).toResiduoResponse(residuo);
    }

    @Test
    void calculoTotalPesoPorPosicao() {
        PosicaoEstoque posicaoEstoque = PosicaoEstoque.builder()
                .id(1L)
                .codigo("Codigo Teste")
                .capacidade(BigDecimal.TEN)
                .status(StatusPosicao.OCUPADA)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(posicaoEstoqueRepository.findById(posicaoEstoque.getId())).thenReturn(Optional.of(posicaoEstoque));
        Mockito.when(residuoRepository.somarPesoPorPosicao(posicaoEstoque.getId())).thenReturn(BigDecimal.valueOf(5));

        BigDecimal total = residuoService.calculoTotalPesoPorPosicao(posicaoEstoque.getId());

        Mockito.verify(posicaoEstoqueRepository).findById(posicaoEstoque.getId());
        Mockito.verify(residuoRepository).somarPesoPorPosicao(posicaoEstoque.getId());
        assertEquals(BigDecimal.valueOf(5), total);
    }
}