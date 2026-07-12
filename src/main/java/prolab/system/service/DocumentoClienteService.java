package prolab.system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prolab.system.domain.Cliente;
import prolab.system.domain.DocumentoCliente;
import prolab.system.domain.Recebimento;
import prolab.system.exception.ClienteNotFoundException;
import prolab.system.exception.DocumentoNotFoundException;
import prolab.system.exception.RecebimentoNotFoundException;
import prolab.system.mapper.DocumentoClienteMapper;
import prolab.system.repository.ClienteRepository;
import prolab.system.repository.DocumentoClienteRepository;
import prolab.system.repository.RecebimentoRepository;
import prolab.system.request.DocumentoClienteRequest;
import prolab.system.response.DocumentoClienteResponse;

@RequiredArgsConstructor
@Service
public class DocumentoClienteService {

    private final ClienteRepository clienteRepository;
    private final RecebimentoRepository recebimentoRepository;
    private final DocumentoClienteRepository documentoClienteRepository;
    private final DocumentoClienteMapper documentoClienteMapper;

    @Transactional
    public DocumentoClienteResponse cadastrar(DocumentoClienteRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado com o ID: " + request.clienteId()));

        Recebimento recebimento = null;
        if (request.recebimentoId() != null) {
            recebimento = recebimentoRepository.findById(request.recebimentoId())
                    .orElseThrow(() -> new RecebimentoNotFoundException("Recebimento não encontrado com o ID: " + request.recebimentoId()));
        }

        DocumentoCliente documentoCliente = documentoClienteMapper.toDocumentoCliente(request);
        documentoCliente.setCliente(cliente);
        documentoCliente.setRecebimento(recebimento);

        DocumentoCliente salvo = documentoClienteRepository.save(documentoCliente);
        return documentoClienteMapper.toDocumentoClienteResponse(salvo);

    }

    @Transactional
    public DocumentoClienteResponse atualizar(Long id, DocumentoClienteRequest request) {
        DocumentoCliente documento = documentoClienteRepository.findById(id)
                .orElseThrow(() -> new DocumentoNotFoundException("Documento não encontrado com o ID: " + id));

        documentoClienteMapper.atualizarDocumentoCliente(request, documento);

        if (request.recebimentoId() != null) {
            Recebimento recebimento = recebimentoRepository.findById(request.recebimentoId())
                    .orElseThrow(() -> new RecebimentoNotFoundException("Recebimento não encontrado com o ID: " + request.recebimentoId()));
            documento.setRecebimento(recebimento);
        }else {
            documento.setRecebimento(null);
        }

        DocumentoCliente salvo = documentoClienteRepository.save(documento);
        return documentoClienteMapper.toDocumentoClienteResponse(salvo);
    }

    public void deletar(Long id) {
        documentoClienteRepository.findById(id)
                .orElseThrow(() -> new DocumentoNotFoundException("Documento não encontrado com o ID: " + id));
        documentoClienteRepository.deleteById(id);
    }

    public DocumentoClienteResponse buscarPorId(Long id) {
        DocumentoCliente documento = documentoClienteRepository.findById(id)
                .orElseThrow(() -> new DocumentoNotFoundException("Documento não encontrado com o ID: " + id));
       return documentoClienteMapper.toDocumentoClienteResponse(documento);
    }

}
