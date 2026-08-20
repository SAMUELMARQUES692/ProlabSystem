package prolab.system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prolab.system.entity.Palete;
import prolab.system.entity.Recebimento;
import prolab.system.exception.RecebimentoNotFoundException;
import prolab.system.mapper.PaleteMapper;
import prolab.system.repository.ControleSequecialTicketRepository;
import prolab.system.repository.PaleteRepository;
import prolab.system.repository.RecebimentoRepository;
import prolab.system.request.PaleteRequest;
import prolab.system.response.PaleteResponse;

import java.time.Year;

@RequiredArgsConstructor
@Service
public class PaleteService {

    private final RecebimentoRepository recebimentoRepository;
    private final ControleSequecialTicketRepository controleSequecialTicketRepository;
    private final PaleteRepository paleteRepository;
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

    private String gerarTicket() {
        int anoAtual = Year.now().getValue();
        Integer numero = controleSequecialTicketRepository.proximoNumero(anoAtual);
        return String.format("TCK-%d-%04d", anoAtual, numero);
    }


}
