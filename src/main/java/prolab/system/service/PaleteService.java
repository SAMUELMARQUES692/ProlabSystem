package prolab.system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prolab.system.entity.Palete;
import prolab.system.entity.PosicaoEstoque;
import prolab.system.entity.Recebimento;
import prolab.system.entity.Residuo;
import prolab.system.enums.StatusPosicao;
import prolab.system.enums.StatusResiduo;
import prolab.system.exception.PaleteNotFoundException;
import prolab.system.exception.PrimeNotFoundException;
import prolab.system.exception.RecebimentoNotFoundException;
import prolab.system.exception.ResiduoNotFoundException;
import prolab.system.mapper.PaleteMapper;
import prolab.system.repository.*;
import prolab.system.request.PaleteRequest;
import prolab.system.response.PaleteResponse;

import java.time.Year;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaleteService {

    private final RecebimentoRepository recebimentoRepository;
    private final ControleSequecialTicketRepository controleSequecialTicketRepository;
    private final PaleteRepository paleteRepository;
    private final PosicaoEstoqueRepository posicaoEstoqueRepository;
    private final ResiduoRepository residuoRepository;
    private final PaleteMapper paleteMapper;

    @Transactional
    public PaleteResponse cadastrar(PaleteRequest request) {
        Recebimento recebimento = recebimentoRepository.findById(request.recebimentoId())
                .orElseThrow(() -> new RecebimentoNotFoundException("Recebimento não encontrado co o ID: " + request.recebimentoId()));

        int proximoNumero = recebimento.getQuantidadePaletes() + 1;

        Palete palete = paleteMapper.toPalete(request);
        palete.setRecebimento(recebimento);
        palete.setNumeroPalete(proximoNumero);
        palete.setTicket(gerarTicket());

        Palete salvo = paleteRepository.save(palete);

        recebimento.setQuantidadePaletes(proximoNumero);
        recebimento.setPesoConferido(recebimento.getPesoConferido().add(palete.getPeso()));
        recebimentoRepository.save(recebimento);

        return paleteMapper.toPaleteResponse(salvo);
    }

    @Transactional
    public PaleteResponse atualizar(Long id, PaleteRequest request) {
        Palete palete = paleteRepository.findById(id)
                .orElseThrow(() -> new PaleteNotFoundException("Palete não encontrado com o ID: " + id));

        paleteMapper.atualizarPalete(request, palete);

        Palete salvo = paleteRepository.save(palete);
        return paleteMapper.toPaleteResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        Palete palete = paleteRepository.findById(id)
                .orElseThrow(() -> new PaleteNotFoundException("Palete não encontrado com o ID: " + id));

        Residuo residuo = palete.getResiduo();
        if (residuo != null) {
            if (residuo.getStatus() != StatusResiduo.DESTRUIDO) {
                PosicaoEstoque posicao = residuo.getPosicaoEstoque();
                posicao.setStatus(StatusPosicao.DISPONIVEL);
                posicaoEstoqueRepository.save(posicao);
            }
            residuoRepository.delete(residuo);
        }
        Recebimento recebimento = palete.getRecebimento();
        recebimento.setPesoConferido(recebimento.getPesoConferido().subtract(palete.getPeso()));
        recebimentoRepository.save(recebimento);

        paleteRepository.delete(palete);
    }

    private String gerarTicket() {
        int anoAtual = Year.now().getValue();
        Integer numero = controleSequecialTicketRepository.proximoNumero(anoAtual);
        return String.format("TCK-%d-%04d", anoAtual, numero);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<PaleteResponse> buscarTodos() {
        return paleteRepository.findAllComRecebimento().stream()
                .map(paleteMapper::toPaleteResponse)
                .toList();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<PaleteResponse> buscarPorPrime(String prime) {
      recebimentoRepository.findByPrime(prime)
               .orElseThrow(() -> new PrimeNotFoundException("Prime não encontrado"));

        return paleteRepository.findAllPaletesPrime(prime).stream()
                .map(paleteMapper::toPaleteResponse)
                .toList();
    }
}
