package prolab.system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prolab.system.domain.Agendamento;
import prolab.system.domain.Caminhao;
import prolab.system.domain.Recebimento;
import prolab.system.exception.RecebimentoDuplicadoException;
import prolab.system.exception.AgendamentoNotFoundException;
import prolab.system.exception.RecebimentoNotFoundException;
import prolab.system.mapper.RecebimentoMapper;
import prolab.system.repository.AgendamentoRepository;
import prolab.system.repository.CaminhaoRepository;
import prolab.system.repository.ControleSequencialRepository;
import prolab.system.repository.RecebimentoRepository;
import prolab.system.request.RecebimentoRequest;
import prolab.system.response.RecebimentoResponse;

import java.time.Year;

@RequiredArgsConstructor
@Service
public class RecebimentoService {

    private final RecebimentoRepository recebimentoRepository;
    private final ControleSequencialRepository controleSequencialRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final CaminhaoRepository caminhaoRepository;
    private final RecebimentoMapper recebimentoMapper;


    @Transactional
    public RecebimentoResponse cadastrar(RecebimentoRequest request) {
        Agendamento agendamento = agendamentoRepository.findById(request.agendamentoId())
                .orElseThrow(() -> new AgendamentoNotFoundException("Agendamento não encontrado"));

        if (recebimentoRepository.findByAgendamentoId(agendamento.getId()).isPresent()) {
            throw new RecebimentoDuplicadoException ("Recebimento já cadastrado para este agendamento");
        }

        Caminhao caminhao = caminhaoRepository.findByPlaca(request.placaCaminhao())
                .orElseGet(() -> criarNovoCaminhao(request));

        Recebimento recebimento = recebimentoMapper.toRecebimento(request);
        recebimento.setAgendamento(agendamento);
        recebimento.setCliente(agendamento.getCliente());
        recebimento.setCaminhao(caminhao);
        recebimento.setPrime(gerarCodigoPrime());

        Recebimento salvo = recebimentoRepository.save(recebimento);
        return recebimentoMapper.toRecebimentoResponse(salvo);
    }

    private String gerarCodigoPrime() {
        int anoAtual = Year.now().getValue();
        Integer numero = controleSequencialRepository.proximoNumero(anoAtual);
        return String.format("PRM-%d-%04d", anoAtual, numero);
    }

    private Caminhao criarNovoCaminhao(RecebimentoRequest request) {
        Caminhao novoCaminhao = Caminhao.builder()
                .placa(request.placaCaminhao())
                .modelo(request.modeloCaminhao())
                .motorista(request.motoristaCaminhao())
                .build();
        return caminhaoRepository.save(novoCaminhao);
    }

    public RecebimentoResponse atualizar(Long id, RecebimentoRequest request) {
        Recebimento recebimento = recebimentoRepository.findById(id)
                .orElseThrow(() -> new RecebimentoNotFoundException("Recebimento não encontrado"));

        recebimentoMapper.atualizarRecebimento(request, recebimento);

        Recebimento salvo = recebimentoRepository.save(recebimento);
        return recebimentoMapper.toRecebimentoResponse(salvo);
    }

    public void deletar(Long id) {
        recebimentoRepository.findById(id)
                .orElseThrow(() -> new RecebimentoNotFoundException("Recebimento não encontrado"));
        recebimentoRepository.deleteById(id);
    }


}
