package prolab.system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import prolab.system.entity.Cliente;
import prolab.system.entity.DocumentoCliente;
import prolab.system.entity.Recebimento;
import prolab.system.enums.TipoDocumento;
import prolab.system.mapper.DocumentoClienteMapper;
import prolab.system.repository.ClienteRepository;
import prolab.system.repository.DocumentoClienteRepository;
import prolab.system.repository.RecebimentoRepository;
import prolab.system.request.DocumentoClienteRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DocumentoClienteServiceTest {

    @InjectMocks
    DocumentoClienteService documentoClienteService;

    @Mock
    DocumentoClienteRepository documentoClienteRepository;

    @Mock
    DocumentoClienteMapper documentoClienteMapper;

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    RecebimentoRepository recebimentoRepository;

    @Test
    void cadastrar() {
      DocumentoCliente documentoCliente = DocumentoCliente.builder()
              .id(1L)
              .cliente(Cliente.builder().id(1L).build())
              .recebimento(Recebimento.builder().id(1L).build())
              .tipo(TipoDocumento.DECLARACAO)
              .numero("12345")
              .arquivoUrl("Url Teste")
              .dataEmissao(LocalDate.now())
              .observacoes("Observacoes Teste")
              .createdAt(LocalDateTime.now())
              .build();

        DocumentoClienteRequest request = DocumentoClienteRequest.builder()
                .clienteId(1L)
                .recebimentoId(1L)
                .tipo(TipoDocumento.DECLARACAO)
                .numero("12345")
                .arquivoUrl("Url Teste")
                .dataEmissao(LocalDate.now())
                .observacoes("Observacoes Teste")
                .build();

        Mockito.when(clienteRepository.findById(request.clienteId())).thenReturn(Optional.of(Cliente.builder().id(1L).build()));
        Mockito.when(recebimentoRepository.findById(request.recebimentoId())).thenReturn(Optional.of(Recebimento.builder().id(1L).build()));
        Mockito.when(documentoClienteMapper.toDocumentoCliente(request)).thenReturn(documentoCliente);
        Mockito.when(documentoClienteRepository.save(documentoCliente)).thenReturn(documentoCliente);

        documentoClienteService.cadastrar(request);

        Mockito.verify(clienteRepository).findById(request.clienteId());
        Mockito.verify(recebimentoRepository).findById(request.recebimentoId());
        Mockito.verify(documentoClienteMapper).toDocumentoCliente(request);
        Mockito.verify(documentoClienteRepository).save(documentoCliente);
        Mockito.verify(documentoClienteMapper).toDocumentoClienteResponse(Mockito.any());
    }

    @Test
    void atualizar() {
        DocumentoCliente documentoCliente = DocumentoCliente.builder()
                .id(1L)
                .cliente(Cliente.builder().id(1L).build())
                .recebimento(Recebimento.builder().id(1L).build())
                .tipo(TipoDocumento.DECLARACAO)
                .numero("12345")
                .arquivoUrl("Url Teste")
                .dataEmissao(LocalDate.now())
                .observacoes("Observacoes Teste")
                .createdAt(LocalDateTime.now())
                .build();

        DocumentoClienteRequest request = DocumentoClienteRequest.builder()
                .clienteId(1L)
                .recebimentoId(1L)
                .tipo(TipoDocumento.DECLARACAO)
                .numero("12345")
                .arquivoUrl("Url Teste")
                .dataEmissao(LocalDate.now())
                .observacoes("Observacoes Teste")
                .build();

        Mockito.when(documentoClienteRepository.findById(documentoCliente.getId())).thenReturn(Optional.of(documentoCliente));
        Mockito.when(recebimentoRepository.findById(request.recebimentoId())).thenReturn(Optional.of(Recebimento.builder().id(1L).build()));
        Mockito.when(documentoClienteRepository.save(documentoCliente)).thenReturn(documentoCliente);

        documentoClienteService.atualizar(documentoCliente.getId(), request);

        Mockito.verify(documentoClienteRepository).findById(documentoCliente.getId());
        Mockito.verify(documentoClienteMapper).atualizarDocumentoCliente(request, documentoCliente);
        Mockito.verify(documentoClienteRepository).save(documentoCliente);
        Mockito.verify(documentoClienteMapper).toDocumentoClienteResponse(Mockito.any());
    }

    @Test
    void deletar() {
        DocumentoCliente documentoCliente = DocumentoCliente.builder()
                .id(1L)
                .cliente(Cliente.builder().id(1L).build())
                .recebimento(Recebimento.builder().id(1L).build())
                .tipo(TipoDocumento.DECLARACAO)
                .numero("12345")
                .arquivoUrl("Url Teste")
                .dataEmissao(LocalDate.now())
                .observacoes("Observacoes Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(documentoClienteRepository.findById(documentoCliente.getId())).thenReturn(Optional.of(documentoCliente));

        documentoClienteService.deletar(documentoCliente.getId());

        Mockito.verify(documentoClienteRepository).findById(documentoCliente.getId());
        Mockito.verify(documentoClienteRepository).deleteById(documentoCliente.getId());
    }


    @Test
    void buscarPorId() {
        DocumentoCliente documentoCliente = DocumentoCliente.builder()
                .id(1L)
                .cliente(Cliente.builder().id(1L).build())
                .recebimento(Recebimento.builder().id(1L).build())
                .tipo(TipoDocumento.DECLARACAO)
                .numero("12345")
                .arquivoUrl("Url Teste")
                .dataEmissao(LocalDate.now())
                .observacoes("Observacoes Teste")
                .createdAt(LocalDateTime.now())
                .build();

       Mockito.when(documentoClienteRepository.findById(documentoCliente.getId())).thenReturn(Optional.of(documentoCliente));

       documentoClienteService.buscarPorId(documentoCliente.getId());

       Mockito.verify(documentoClienteRepository).findById(documentoCliente.getId());
       Mockito.verify(documentoClienteMapper).toDocumentoClienteResponse(Mockito.any());
    }
}