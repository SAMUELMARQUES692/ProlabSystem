package prolab.system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import prolab.system.entity.Agendamento;
import prolab.system.entity.Cliente;
import prolab.system.enums.StatusAgendamento;
import prolab.system.enums.TipoDeDestruicao;
import prolab.system.mapper.AgendamentoMapper;
import prolab.system.repository.AgendamentoRepository;
import prolab.system.repository.ClienteRepository;
import prolab.system.request.AgendamentoRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @InjectMocks
    AgendamentoService agendamentoService;

    @Mock
    AgendamentoRepository agendamentoRepository;
    @Mock
    AgendamentoMapper agendamentoMapper;

    @Mock
    ClienteRepository clienteRepository;

    @Captor
    ArgumentCaptor<Agendamento> argumentCaptor;


    @Test
    void cadastrar() {

        Cliente cliente = Cliente.builder()
                .id(1L)
                .razaoSocial("Cliente Teste")
                .cnpj("12345678910123")
                .contato("Contato Teste")
                .endereco("Endereco Teste")
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();


        // Arrange - Given
        AgendamentoRequest request = AgendamentoRequest.builder()
                .clienteId(cliente.getId())
                .tipoResiduo("Residuo Teste")
                .tipoDeDestruicao(TipoDeDestruicao.DESTRUICAO_DIRETA)
                .quantidadePaletes(10)
                .dataHoraPrevista(LocalDateTime.now())
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

        Mockito.when(agendamentoMapper.toAgendamento(request)).thenReturn(agendamento);
        Mockito.when(clienteRepository.findById(request.clienteId())).thenReturn(Optional.of(cliente));

        // Action - When
        agendamentoService.cadastrar(request);

        // Assertions - Then
        Mockito.verify(clienteRepository).findById(request.clienteId()); // este codigo verifica se os clienteRepository esta sendo chamado no metodo.
        Mockito.verify(agendamentoMapper).toAgendamento(request); // este codigo verifica se os agendamentoMapper esta sendo chamado no metodo.
        Mockito.verify(agendamentoRepository).save(Mockito.any()); // verifica se o metodo save do agendamentoRepository esta sendo chamado.
        Mockito.verify(agendamentoMapper).toAgendamentoResponse(Mockito.any()); // veriica se o AgendamentoResponse esta sendo retornado no metodo.
        Mockito.verify(agendamentoRepository).save(argumentCaptor.capture());
    }

    @Test
    void atualizar() {

        Cliente cliente = Cliente.builder()
                .id(1L)
                .razaoSocial("Cliente Teste")
                .cnpj("12345678910123")
                .contato("Contato Teste")
                .endereco("Endereco Teste")
                .ativo(true)
                .createdAt(LocalDateTime.now())
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

        AgendamentoRequest request = AgendamentoRequest.builder()
                .clienteId(cliente.getId())
                .tipoResiduo("Residuo Teste")
                .tipoDeDestruicao(TipoDeDestruicao.DESTRUICAO_DIRETA)
                .quantidadePaletes(10)
                .dataHoraPrevista(LocalDateTime.now())
                .build();

        Mockito.when(agendamentoRepository.findById(agendamento.getId())).thenReturn(Optional.of(agendamento));

        agendamentoService.atualizar(agendamento.getId(), request);

        Mockito.verify(agendamentoRepository).findById(agendamento.getId());
        Mockito.verify(agendamentoMapper).atualizarAgendamento(request, agendamento);
        Mockito.verify(agendamentoRepository).save(Mockito.any());
        Mockito.verify(agendamentoMapper).toAgendamentoResponse(Mockito.any());
        Mockito.verify(agendamentoRepository).save(argumentCaptor.capture());
    }

    @Test
    void deletar() {

        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .tipoResiduo("Residuo Teste")
                .tipoDeDestruicao(TipoDeDestruicao.DESTRUICAO_DIRETA)
                .quantidadePaletes(10)
                .dataHoraPrevista(LocalDateTime.now())
                .status(StatusAgendamento.AGENDADO)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(agendamentoRepository.findById(agendamento.getId())).thenReturn(Optional.of(agendamento));

        agendamentoService.deletar(agendamento.getId());

        Mockito.verify(agendamentoRepository).findById(agendamento.getId());
        Mockito.verify(agendamentoRepository).deleteById(agendamento.getId());
    }

    @Test
    void buscarPorCliente() {

        Cliente cliente = Cliente.builder()
                .id(1L)
                .razaoSocial("Cliente Teste")
                .cnpj("12345678910123")
                .contato("Contato Teste")
                .endereco("Endereco Teste")
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();


        Mockito.when(agendamentoRepository.findByClienteId(cliente.getId())).thenReturn(List.of(
                Agendamento.builder()
                        .id(1L)
                        .tipoResiduo("Residuo Teste")
                        .tipoDeDestruicao(TipoDeDestruicao.DESTRUICAO_DIRETA)
                        .quantidadePaletes(10)
                        .dataHoraPrevista(LocalDateTime.now())
                        .status(StatusAgendamento.AGENDADO)
                        .createdAt(LocalDateTime.now())
                        .build()
        ));

        agendamentoService.buscarPorCliente(cliente.getId());

        Mockito.verify(agendamentoRepository).findByClienteId(cliente.getId());
        Mockito.verify(agendamentoMapper).toAgendamentoResponse(Mockito.any());
    }

    @Test
    void buscarPorStatus() {
        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .tipoResiduo("Residuo Teste")
                .tipoDeDestruicao(TipoDeDestruicao.DESTRUICAO_DIRETA)
                .quantidadePaletes(10)
                .dataHoraPrevista(LocalDateTime.now())
                .status(StatusAgendamento.AGENDADO)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(agendamentoRepository.findByStatus(agendamento.getStatus())).thenReturn(List.of(agendamento));

        agendamentoService.buscarPorStatus(agendamento.getStatus());

        Mockito.verify(agendamentoRepository).findByStatus(agendamento.getStatus());
        Mockito.verify(agendamentoMapper).toAgendamentoResponse(Mockito.any());
    }

    @Test
    void buscarPorTipoDeDestruicao() {
        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .tipoResiduo("Residuo Teste")
                .tipoDeDestruicao(TipoDeDestruicao.DESTRUICAO_DIRETA)
                .quantidadePaletes(10)
                .dataHoraPrevista(LocalDateTime.now())
                .status(StatusAgendamento.AGENDADO)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(agendamentoRepository.findByTipoDeDestruicao(agendamento.getTipoDeDestruicao())).thenReturn(List.of(agendamento));

        agendamentoService.buscarPorTipoDeDestruicao(agendamento.getTipoDeDestruicao());

        Mockito.verify(agendamentoRepository).findByTipoDeDestruicao(agendamento.getTipoDeDestruicao());
        Mockito.verify(agendamentoMapper).toAgendamentoResponse(Mockito.any());
    }
}