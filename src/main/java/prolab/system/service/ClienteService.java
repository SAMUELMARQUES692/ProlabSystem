package prolab.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prolab.system.domain.Cliente;
import prolab.system.mapper.ClienteMapper;
import prolab.system.repository.ClienteRepository;
import prolab.system.repository.ControleSequencialRepository;
import prolab.system.request.ClienteRequest;
import prolab.system.response.ClienteResponse;

import java.time.Year;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Transactional
    public ClienteResponse cadastrar(ClienteRequest request) {

        if (clienteRepository.findByCnpj(request.cnpj()).isPresent()) {
            throw new DataIntegrityViolationException("CNPJ já cadastrado");
        }

        Cliente novoCliente = clienteMapper.toCliente(request);
        Cliente clienteSalvo = clienteRepository.save(novoCliente);
        return clienteMapper.toClienteResponse(clienteSalvo);
    }
}
