package prolab.system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import prolab.system.entity.Agendamento;
import prolab.system.entity.Caminhao;
import prolab.system.entity.Cliente;
import prolab.system.entity.Recebimento;
import prolab.system.enums.StatusAgendamento;
import prolab.system.enums.TipoDeDestruicao;
import prolab.system.mapper.RecebimentoMapper;
import prolab.system.repository.AgendamentoRepository;
import prolab.system.repository.CaminhaoRepository;
import prolab.system.repository.ControleSequencialRepository;
import prolab.system.repository.RecebimentoRepository;
import prolab.system.request.RecebimentoRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RecebimentoServiceTest {

    @InjectMocks
    RecebimentoService recebimentoService;

    @Mock
    RecebimentoMapper recebimentoMapper;

    @Mock
    RecebimentoRepository recebimentoRepository;

    @Mock
    AgendamentoRepository agendamentoRepository;

    @Mock
    CaminhaoRepository caminhaoRepository;

    @Mock
    ControleSequencialRepository controleSequencialRepository;

    @Captor
    ArgumentCaptor<Recebimento> argumentCaptor;

    @Test
    void cadastrar() {
        Recebimento recebimento = Recebimento.builder()
                .id(1L)
                .agendamento(Agendamento.builder().id(1L).build())
                .cliente(Cliente.builder().id(1L).build())
                .caminhao(Caminhao.builder().id(1L).build())
                .prime("Prime Teste")
                .dataHoraRecebimento(LocalDateTime.now())
                .pesoConferido(null)
                .observacoes("Observacao Teste")
                .createdAt(LocalDateTime.now())
                .build();

        RecebimentoRequest request = RecebimentoRequest.builder()
                .agendamentoId(1L)
                .placaCaminhao("ABC1234")
                .motoristaCaminhao("Marcos")
                .dataHoraRecebimento(LocalDateTime.now())
                .pesoConferido(null)
                .observacoes("Observacao Teste")
                .build();

        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .tipoResiduo("Residuo Teste")
                .tipoDeDestruicao(TipoDeDestruicao.DESTRUICAO_DIRETA)
                .quantidadePaletes(10)
                .dataHoraPrevista(LocalDateTime.now())
                .status(StatusAgendamento.AGENDADO)
                .createdAt(LocalDateTime.now())
                .build();

        Caminhao caminhao = Caminhao.builder()
                .id(1L)
                .placa("ABC1234")
                .modelo("Modelo Teste")
                .motorista("Motorista Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(agendamentoRepository.findById(request.agendamentoId())).thenReturn(Optional.of(agendamento));
        Mockito.when(recebimentoRepository.findByAgendamentoId(recebimento.getAgendamento().getId())).thenReturn(Optional.empty());
        Mockito.when(caminhaoRepository.findByPlaca(caminhao.getPlaca())).thenReturn(Optional.empty());
        Mockito.when(recebimentoMapper.toRecebimento(request)).thenReturn(recebimento);
        Mockito.when(controleSequencialRepository.proximoNumero(Mockito.anyInt())).thenReturn(1);
        Mockito.when(caminhaoRepository.save(Mockito.any())).thenReturn(caminhao);

        recebimentoService.cadastrar(request);

        Mockito.verify(agendamentoRepository).findById(agendamento.getId());
        Mockito.verify(recebimentoRepository).findByAgendamentoId(request.agendamentoId());
        Mockito.verify(caminhaoRepository).findByPlaca(caminhao.getPlaca());
        Mockito.verify(recebimentoMapper).toRecebimento(request);
        Mockito.verify(recebimentoRepository).save(argumentCaptor.capture());
        Mockito.verify(recebimentoMapper).toRecebimentoResponse(Mockito.any());
        Mockito.verify(caminhaoRepository).save(Mockito.any());

        Recebimento salvo = argumentCaptor.getValue();
        assertNotNull(salvo.getPrime());
        assertEquals(agendamento, salvo.getAgendamento());
    }

    @Test
    void atualizar() {
        Recebimento recebimento = Recebimento.builder()
                .id(1L)
                .agendamento(Agendamento.builder().id(1L).build())
                .cliente(Cliente.builder().id(1L).build())
                .caminhao(Caminhao.builder().id(1L).build())
                .prime("Prime Teste")
                .dataHoraRecebimento(LocalDateTime.now())
                .pesoConferido(null)
                .observacoes("Observacao Teste")
                .createdAt(LocalDateTime.now())
                .build();

        RecebimentoRequest request = RecebimentoRequest.builder()
                .agendamentoId(1L)
                .placaCaminhao("ABC1234")
                .motoristaCaminhao("Marcos")
                .dataHoraRecebimento(LocalDateTime.now())
                .pesoConferido(null)
                .observacoes("Observacao Teste")
                .build();

        Mockito.when(recebimentoRepository.findById(recebimento.getId())).thenReturn(Optional.of(recebimento));

        recebimentoService.atualizar(recebimento.getId(), request);

        Mockito.verify(recebimentoRepository).findById(recebimento.getId());
        Mockito.verify(recebimentoMapper).atualizarRecebimento(request, recebimento);
        Mockito.verify(recebimentoRepository).save(argumentCaptor.capture());
        Mockito.verify(recebimentoMapper).toRecebimentoResponse(Mockito.any());
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
                .pesoConferido(null)
                .observacoes("Observacao Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(recebimentoRepository.findById(recebimento.getId())).thenReturn(Optional.of(recebimento));

        recebimentoService.deletar(recebimento.getId());

        Mockito.verify(recebimentoRepository).findById(recebimento.getId());
        Mockito.verify(recebimentoRepository).deleteById(recebimento.getId());
    }
}