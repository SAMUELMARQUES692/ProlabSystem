package prolab.system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import prolab.system.entity.*;
import prolab.system.enums.StatusPosicao;
import prolab.system.enums.StatusResiduo;
import prolab.system.mapper.ResiduoMapper;
import prolab.system.repository.PosicaoEstoqueRepository;
import prolab.system.repository.RecebimentoRepository;
import prolab.system.repository.ResiduoRepository;
import prolab.system.request.ResiduoRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Captor
    ArgumentCaptor<Residuo> argumentCaptor;

    @Test
    void cadastrar() {
        Residuo residuo = Residuo.builder()
                .id(1L)
                .recebimento(Recebimento.builder().id(1L).build())
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
                .posicaoEstoque(PosicaoEstoque.builder().id(1L).build())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        ResiduoRequest request = ResiduoRequest.builder()
                .recebimentoId(1L)
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
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

        PosicaoEstoque posicaoEstoque = PosicaoEstoque.builder()
                .id(1L)
                .codigo("Codigo Teste")
                .capacidade(BigDecimal.TEN)
                .status(StatusPosicao.OCUPADA)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(recebimentoRepository.findById(request.recebimentoId())).thenReturn(Optional.of(recebimento));
        Mockito.when(posicaoEstoqueRepository.findById(request.posicaoId())).thenReturn(Optional.of(posicaoEstoque));
        Mockito.when(residuoRepository.somarQuantidadePorPosicao(request.posicaoId())).thenReturn(BigDecimal.ZERO); // ou qualquer valor que faça sentido pro cenário
        Mockito.when(residuoMapper.toResiduo(request)).thenReturn(residuo);

        residuoService.cadastrar(request);

        Mockito.verify(recebimentoRepository).findById(request.recebimentoId());
        Mockito.verify(posicaoEstoqueRepository).findById(request.posicaoId());
        Mockito.verify(residuoRepository).somarQuantidadePorPosicao(request.posicaoId());
        Mockito.verify(residuoMapper).toResiduo(request);
        Mockito.verify(residuoRepository).save(argumentCaptor.capture());
        Mockito.verify(residuoMapper).toResiduoResponse(Mockito.any());
    }

    @Test
    void atualizar() {
        Residuo residuo = Residuo.builder()
                .id(1L)
                .recebimento(Recebimento.builder().id(1L).build())
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
                .posicaoEstoque(PosicaoEstoque.builder().id(1L).build())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        ResiduoRequest request = ResiduoRequest.builder()
                .recebimentoId(1L)
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
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
                .recebimento(Recebimento.builder().id(1L).build())
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
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
                .recebimento(Recebimento.builder().id(1L).build())
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
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
                .recebimento(Recebimento.builder().id(1L).build())
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
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
    void buscarPorTipoResiduo() {
        Residuo residuo = Residuo.builder()
                .id(1L)
                .recebimento(Recebimento.builder().id(1L).build())
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
                .posicaoEstoque(PosicaoEstoque.builder().id(1L).build())
                .status(StatusResiduo.ARMAZENADO)
                .mtrVinculado("MTR Teste")
                .dataDestinacao(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(residuoRepository.findByTipoResiduo(residuo.getTipoResiduo())).thenReturn(List.of(residuo));

        residuoService.buscarPorTipoResiduo(residuo.getTipoResiduo());

        Mockito.verify(residuoRepository).findByTipoResiduo(residuo.getTipoResiduo());
        Mockito.verify(residuoMapper).toResiduoResponse(residuo);
    }

    @Test
    void buscarPorPosicao() {
        Residuo residuo = Residuo.builder()
                .id(1L)
                .recebimento(Recebimento.builder().id(1L).build())
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
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
                .recebimento(Recebimento.builder().id(1L).build())
                .tipoResiduo("Tipo Teste")
                .quantidade(BigDecimal.TEN)
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

        Mockito.when(posicaoEstoqueRepository.findById(posicaoEstoque.getId())).thenReturn(Optional.empty());

        residuoService.calculoTotalPesoPorPosicao(posicaoEstoque.getId());

        Mockito.verify(posicaoEstoqueRepository).findById(posicaoEstoque.getId());
        Mockito.verify(residuoRepository).somarQuantidadePorPosicao(posicaoEstoque.getId());
    }
}