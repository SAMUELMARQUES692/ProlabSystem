package prolab.system.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import prolab.system.entity.DocumentoCliente;
import prolab.system.enums.TipoDocumento;
import prolab.system.request.DocumentoClienteRequest;
import prolab.system.response.DocumentoClienteResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DocumentoClienteMapperTest {

    private final DocumentoClienteMapper mapper = Mappers.getMapper(DocumentoClienteMapper.class);

    @Test
    void toDocumentoCliente() {

        DocumentoClienteRequest request = DocumentoClienteRequest.builder()
                .tipo(TipoDocumento.DECLARACAO)
                .numero("Numero Teste")
                .arquivoUrl("Arquivo Teste")
                .dataEmissao(LocalDate.now())
                .observacoes("Observacoes Teste")
                .build();

        DocumentoCliente documentoCliente = mapper.toDocumentoCliente(request);

        assertNotNull(documentoCliente);

        assertEquals(request.tipo(), documentoCliente.getTipo());
        assertEquals(request.numero(), documentoCliente.getNumero());
        assertEquals(request.arquivoUrl(), documentoCliente.getArquivoUrl());
        assertEquals(request.dataEmissao(), documentoCliente.getDataEmissao());
        assertEquals(request.observacoes(), documentoCliente.getObservacoes());
    }

    @Test
    void toDocumentoClienteResponse() {

        DocumentoCliente documentoCliente = DocumentoCliente.builder()
                .id(1L)
                .tipo(TipoDocumento.DECLARACAO)
                .numero("Numero Teste")
                .arquivoUrl("Arquivo Teste")
                .dataEmissao(LocalDate.now())
                .observacoes("Observacoes Teste")
                .createdAt(LocalDateTime.now())
                .build();

        DocumentoClienteResponse response = mapper.toDocumentoClienteResponse(documentoCliente);

        assertNotNull(response);

        assertEquals(documentoCliente.getId(), response.id());
        assertEquals(documentoCliente.getTipo(), response.tipo());
        assertEquals(documentoCliente.getNumero(), response.numero());
        assertEquals(documentoCliente.getArquivoUrl(), response.arquivoUrl());
        assertEquals(documentoCliente.getDataEmissao(), response.dataEmissao());
        assertEquals(documentoCliente.getObservacoes(), response.observacoes());
        assertEquals(documentoCliente.getCreatedAt(), response.createdAt());
    }

    @Test
    void atualizarDocumentoCliente() {
        DocumentoClienteRequest request = DocumentoClienteRequest.builder()
                .tipo(TipoDocumento.DECLARACAO)
                .numero("Numero Teste")
                .arquivoUrl("Arquivo Teste")
                .dataEmissao(LocalDate.now())
                .observacoes("Observacoes Teste")
                .build();

        DocumentoCliente documentoCliente = DocumentoCliente.builder()
                .id(1L)
                .tipo(TipoDocumento.DECLARACAO)
                .numero("Numero Teste")
                .arquivoUrl("Arquivo Teste")
                .dataEmissao(LocalDate.now())
                .observacoes("Observacoes Teste")
                .createdAt(LocalDateTime.now())
                .build();

        mapper.atualizarDocumentoCliente(request, documentoCliente);

        assertEquals(request.tipo(), documentoCliente.getTipo());
        assertEquals(request.numero(), documentoCliente.getNumero());
        assertEquals(request.arquivoUrl(), documentoCliente.getArquivoUrl());
        assertEquals(request.dataEmissao(), documentoCliente.getDataEmissao());
        assertEquals(request.observacoes(), documentoCliente.getObservacoes());
    }
}