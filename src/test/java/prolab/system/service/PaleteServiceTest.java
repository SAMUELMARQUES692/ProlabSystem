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
import prolab.system.mapper.PaleteMapper;
import prolab.system.repository.*;
import prolab.system.request.PaleteRequest;
import prolab.system.response.PaleteResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaleteServiceTest {

    @InjectMocks
    PaleteService paleteService;

    @Mock
    RecebimentoRepository recebimentoRepository;

    @Mock
    PaleteRepository paleteRepository;

    @Mock
    PosicaoEstoqueRepository posicaoEstoqueRepository;

    @Mock
    ResiduoRepository residuoRepository;

    @Mock
    PaleteMapper paleteMapper;

    @Captor
    ArgumentCaptor<Palete> argumentCaptor;

    @Test
    void cadastrar() {
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
                .numeroPalete(3)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.ONE)
                .estadoFisico(EstadoFisico.SOLIDO)
                .createdAt(LocalDateTime.now())
                .build();

        PaleteRequest request = PaleteRequest.builder()
                .recebimentoId(1L)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.TEN)
                .estadoFisico(EstadoFisico.SOLIDO)
                .build();

        PaleteResponse response = PaleteResponse.builder()
                .id(1L)
                .ticket("Ticket Teste")
                .numeroPalete(3)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.ONE)
                .estadoFisico(EstadoFisico.SOLIDO)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(recebimentoRepository.findById(request.recebimentoId())).thenReturn(Optional.of(recebimento));
        Mockito.when(paleteMapper.toPalete(request)).thenReturn(palete);
        Mockito.when(paleteRepository.save(palete)).thenReturn(palete);
        Mockito.when(recebimentoRepository.save(recebimento)).thenReturn(recebimento);
        Mockito.when(paleteMapper.toPaleteResponse(palete)).thenReturn(response);

        paleteService.cadastrar(request);

        Mockito.verify(recebimentoRepository).findById(request.recebimentoId());
        Mockito.verify(paleteMapper).toPalete(request);
        Mockito.verify(paleteRepository).save(palete);
        Mockito.verify(recebimentoRepository).save(recebimento);
        Mockito.verify(paleteMapper).toPaleteResponse(palete);
        Mockito.verify(paleteRepository).save(argumentCaptor.capture());
    }

    @Test
    void atualizar() {
        Palete palete = Palete.builder()
                .id(1L)
                .ticket("Ticket Teste")
                .numeroPalete(3)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.ONE)
                .estadoFisico(EstadoFisico.SOLIDO)
                .createdAt(LocalDateTime.now())
                .build();

        PaleteRequest request = PaleteRequest.builder()
                .recebimentoId(1L)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.TEN)
                .estadoFisico(EstadoFisico.SOLIDO)
                .build();

        PaleteResponse response = PaleteResponse.builder()
                .id(1L)
                .ticket("Ticket Teste")
                .numeroPalete(3)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.ONE)
                .estadoFisico(EstadoFisico.SOLIDO)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(paleteRepository.findById(palete.getId())).thenReturn(Optional.of(palete));
        Mockito.when(paleteRepository.save(palete)).thenReturn(palete);
        Mockito.when(paleteMapper.toPaleteResponse(palete)).thenReturn(response);

        paleteService.atualizar(palete.getId(), request);

        Mockito.verify(paleteRepository).findById(palete.getId());
        Mockito.verify(paleteMapper).atualizarPalete(request, palete);
        Mockito.verify(paleteRepository).save(argumentCaptor.capture());
        Mockito.verify(paleteMapper).toPaleteResponse(palete);
    }

    @Test
    void deletar() {
        Recebimento recebimento = Recebimento.builder()
                .id(1L)
                .agendamento(Agendamento.builder().id(1L).build())
                .cliente(Cliente.builder().id(1L).build())
                .caminhao(Caminhao.builder().id(1L).build())
                .prime("Prime Teste")
                .dataHoraRecebimento(LocalDateTime.now())
                .pesoConferido(BigDecimal.ZERO)
                .observacoes("Observacao Teste")
                .createdAt(LocalDateTime.now())
                .build();

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

        Palete palete = Palete.builder()
                .id(1L)
                .ticket("Ticket Teste")
                .numeroPalete(3)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.ONE)
                .estadoFisico(EstadoFisico.SOLIDO)
                .recebimento(recebimento)
                .residuo(residuo)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(paleteRepository.findById(palete.getId())).thenReturn(Optional.of(palete));

        paleteService.deletar(palete.getId());

        Mockito.verify(paleteRepository).findById(palete.getId());
        Mockito.verify(posicaoEstoqueRepository).save(posicaoEstoque);
        Mockito.verify(residuoRepository).delete(residuo);
        Mockito.verify(recebimentoRepository).save(recebimento);
        Mockito.verify(paleteRepository).delete(palete);

    }

    @Test
    void buscarTodos() {
        Palete palete = Palete.builder()
                .id(1L)
                .ticket("Ticket Teste")
                .numeroPalete(3)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.ONE)
                .estadoFisico(EstadoFisico.SOLIDO)
                .createdAt(LocalDateTime.now())
                .build();

        PaleteResponse response = PaleteResponse.builder()
                .id(1L)
                .ticket("Ticket Teste")
                .numeroPalete(3)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.ONE)
                .estadoFisico(EstadoFisico.SOLIDO)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(paleteRepository.findAllComRecebimento()).thenReturn(List.of(palete));
        Mockito.when(paleteMapper.toPaleteResponse(palete)).thenReturn(response);

        paleteService.buscarTodos();

        Mockito.verify(paleteRepository).findAllComRecebimento();
        Mockito.verify(paleteMapper).toPaleteResponse(palete);
    }

    @Test
    void buscarPorPrime() {
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
                .numeroPalete(3)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.ONE)
                .estadoFisico(EstadoFisico.SOLIDO)
                .createdAt(LocalDateTime.now())
                .build();

        PaleteResponse response = PaleteResponse.builder()
                .id(1L)
                .ticket("Ticket Teste")
                .numeroPalete(3)
                .tipo(TipoResiduo.CODIGO_15_02_02)
                .peso(BigDecimal.ONE)
                .estadoFisico(EstadoFisico.SOLIDO)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(recebimentoRepository.findByPrime(recebimento.getPrime())).thenReturn(Optional.of(recebimento));
        Mockito.when(paleteRepository.findAllPaletesPrime(recebimento.getPrime())).thenReturn(List.of(palete));
        Mockito.when(paleteMapper.toPaleteResponse(palete)).thenReturn(response);

        paleteService.buscarPorPrime(recebimento.getPrime());

        Mockito.verify(recebimentoRepository).findByPrime(recebimento.getPrime());
        Mockito.verify(paleteRepository).findAllPaletesPrime(recebimento.getPrime());
        Mockito.verify(paleteMapper).toPaleteResponse(palete);
    }

}