package prolab.system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prolab.system.domain.Cliente;
import prolab.system.domain.DocumentoCliente;
import prolab.system.domain.Recebimento;
import prolab.system.exception.ClienteNotFoundException;
import prolab.system.exception.RecebimentoDuplicadoException;
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
                    .orElseThrow(() -> new RecebimentoDuplicadoException("Recebimento não encontrado com o ID: " + request.recebimentoId()));
        }

        DocumentoCliente documentoCliente = documentoClienteMapper.toDocumentoCliente(request);
        documentoCliente.setCliente(cliente);
        documentoCliente.setRecebimento(recebimento);

        DocumentoCliente salvo = documentoClienteRepository.save(documentoCliente);
        return documentoClienteMapper.toDocumentoClienteResponse(salvo);

    }

}
