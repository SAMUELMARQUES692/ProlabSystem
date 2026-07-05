package prolab.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prolab.system.domain.Agendamento;
import prolab.system.domain.Cliente;
import prolab.system.enums.StatusAgendamento;
import prolab.system.exception.AgendamentoNotFoundException;
import prolab.system.exception.ClienteNotFoundException;
import prolab.system.mapper.AgendamentoMapper;
import prolab.system.repository.AgendamentoRepository;
import prolab.system.repository.ClienteRepository;
import prolab.system.request.AgendamentoRequest;
import prolab.system.response.AgendamentoResponse;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AgendamentoService {

    private final ClienteRepository clienteRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AgendamentoMapper agendamentoMapper;

    public AgendamentoResponse cadastrar(AgendamentoRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado"));

        Agendamento agendamento = agendamentoMapper.toAgendamento(request);
        agendamento.setCliente(cliente);
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        Agendamento salvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toAgendamentoResponse(salvo);
    }

    public AgendamentoResponse atualizar(Long id, AgendamentoRequest request) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new AgendamentoNotFoundException("Agendamento não encontrado"));

        agendamentoMapper.atualizarAgendamento(request, agendamento);

        Agendamento salvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toAgendamentoResponse(salvo);
    }

    public void deletar(Long id) {
        agendamentoRepository.findById(id)
                .orElseThrow(() -> new AgendamentoNotFoundException("Agendamento não encontrado"));
        agendamentoRepository.deleteById(id);
    }

    public List<AgendamentoResponse> buscarPorCliente(Long clienteId) {
        return agendamentoRepository.findByClienteId(clienteId).stream()
                .map(agendamentoMapper::toAgendamentoResponse)
                .toList();
    }

    public List<AgendamentoResponse> buscarPorStatus(StatusAgendamento status) {
        return agendamentoRepository.findByStatus(status).stream()
                .map(agendamentoMapper::toAgendamentoResponse)
                .toList();
    }



}
