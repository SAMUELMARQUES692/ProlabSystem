package prolab.system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import prolab.system.entity.Cliente;
import prolab.system.mapper.ClienteMapper;
import prolab.system.repository.ClienteRepository;
import prolab.system.request.ClienteRequest;
import prolab.system.response.ClienteResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    ClienteService clienteService;

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    ClienteMapper clienteMapper;

    @Captor
    ArgumentCaptor<Cliente> argumentCaptor;

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

        ClienteRequest request = ClienteRequest.builder()
                .razaoSocial("Cliente Teste")
                .cnpj("12345678910124")
                .contato("Contato Teste")
                .endereco("Endereco Teste")
                .build();

        Mockito.when(clienteRepository.findByCnpj(request.cnpj())).thenReturn(Optional.empty());
        Mockito.when(clienteMapper.toCliente(request)).thenReturn(cliente);
        Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);

        clienteService.cadastrar(request);

        Mockito.verify(clienteRepository).findByCnpj(request.cnpj());
        Mockito.verify(clienteRepository).save(cliente);
        Mockito.verify(clienteMapper).toCliente(Mockito.any());
        Mockito.verify(clienteMapper).toClienteResponse(Mockito.any());
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

        ClienteRequest request = ClienteRequest.builder()
                .razaoSocial("Cliente Teste")
                .cnpj("12345678910124")
                .contato("Contato Teste")
                .endereco("Endereco Teste")
                .build();

        Mockito.when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        clienteService.atualizar(cliente.getId(), request);

        Mockito.verify(clienteRepository).findById(cliente.getId());
        Mockito.verify(clienteMapper).atualizarCliente(request, cliente);
        Mockito.verify(clienteRepository).save(cliente);
        Mockito.verify(clienteMapper).toClienteResponse(Mockito.any());
    }

    @Test
    void deletar() {
        Cliente cliente = Cliente.builder()
                .id(1L)
                .razaoSocial("Cliente Teste")
                .cnpj("12345678910123")
                .contato("Contato Teste")
                .endereco("Endereco Teste")
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);

        clienteService.deletar(cliente.getId());

        Mockito.verify(clienteRepository).findById(cliente.getId());
        Mockito.verify(clienteRepository).save(Mockito.any());
        Mockito.verify(clienteRepository).save(argumentCaptor.capture());

        assertFalse(argumentCaptor.getValue().getAtivo());
    }

    @Test
    void buscarPorId() {
        Cliente cliente = Cliente.builder()
                .id(1L)
                .razaoSocial("Cliente Teste")
                .cnpj("12345678910123")
                .contato("Contato Teste")
                .endereco("Endereco Teste")
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        clienteService.buscarPorId(cliente.getId());

        Mockito.verify(clienteRepository).findById(cliente.getId());
        Mockito.verify(clienteMapper).toClienteResponse(Mockito.any());
    }

    @Test
    void buscarTodos() {
        Cliente cliente = Cliente.builder()
                .id(1L)
                .razaoSocial("Cliente Teste")
                .cnpj("12345678910123")
                .contato("Contato Teste")
                .endereco("Endereco Teste")
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(clienteRepository.findByAtivoTrue()).thenReturn(List.of(cliente));

        clienteService.buscarTodos();

        Mockito.verify(clienteRepository).findByAtivoTrue();
        Mockito.verify(clienteMapper).toClienteResponse(Mockito.any());
    }

    @Test
    void buscarPorRazaoSocial() {
        Cliente cliente = Cliente.builder()
                .id(1L)
                .razaoSocial("Cliente Teste")
                .cnpj("12345678910123")
                .contato("Contato Teste")
                .endereco("Endereco Teste")
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(clienteRepository.findByRazaoSocial(cliente.getRazaoSocial())).thenReturn(Optional.of(cliente));

        clienteService.buscarPorRazaoSocial(cliente.getRazaoSocial());

        Mockito.verify(clienteRepository).findByRazaoSocial(cliente.getRazaoSocial());
        Mockito.verify(clienteMapper).toClienteResponse(Mockito.any());
    }
}