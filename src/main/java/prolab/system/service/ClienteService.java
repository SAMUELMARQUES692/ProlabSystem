package prolab.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prolab.system.entity.Cliente;
import prolab.system.exception.ClienteNotFoundException;
import prolab.system.mapper.ClienteMapper;
import prolab.system.repository.ClienteRepository;
import prolab.system.request.ClienteRequest;
import prolab.system.response.ClienteResponse;

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

    @Transactional
    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado com o ID " + id));

        clienteMapper.atualizarCliente(request, cliente);

        Cliente salvo = clienteRepository.save(cliente);
        return clienteMapper.toClienteResponse(salvo);
    }

    public void deletar(Long id) {
        clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado com o ID " + id));
        clienteRepository.deleteById(id);
    }

    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado com o ID " + id));
        return clienteMapper.toClienteResponse(cliente);
    }


}
