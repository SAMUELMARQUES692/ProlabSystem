package prolab.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prolab.system.domain.PosicaoEstoque;
import prolab.system.domain.Recebimento;
import prolab.system.domain.Residuo;
import prolab.system.enums.StatusResiduo;
import prolab.system.exception.PosicaoNotFoundException;
import prolab.system.exception.RecebimentoNotFoundException;
import prolab.system.exception.ResiduoNotFoundException;
import prolab.system.exception.TransicaoStatusInvalidaException;
import prolab.system.mapper.ResiduoMapper;
import prolab.system.repository.PosicaoEstoqueRepository;
import prolab.system.repository.RecebimentoRepository;
import prolab.system.repository.ResiduoRepository;
import prolab.system.request.ResiduoRequest;
import prolab.system.response.ResiduoResponse;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ResiduoService {

    private final ResiduoRepository residuoRepository;
    private final ResiduoMapper residuoMapper;
    private final RecebimentoRepository recebimentoRepository;
    private final PosicaoEstoqueRepository posicaoEstoqueRepository;


    public ResiduoResponse cadastrar(ResiduoRequest request) {
        Recebimento recebimento = recebimentoRepository.findById(request.recebimentoId())
                .orElseThrow(() -> new RecebimentoNotFoundException("Recebimento não encontrado com ID: " + request.recebimentoId()));

        PosicaoEstoque posicao = posicaoEstoqueRepository.findById(request.posicaoId())
                .orElseThrow(() -> new PosicaoNotFoundException("Posição não encontrada com o ID: " + request.posicaoId()));

        Residuo residuo = residuoMapper.toResiduo(request);
        residuo.setRecebimento(recebimento);
        residuo.setPosicaoEstoque(posicao);
        residuo.setStatus(StatusResiduo.ARMAZENADO);

        Residuo salvo = residuoRepository.save(residuo);
        return residuoMapper.toResiduoResponse(salvo);
    }

    public ResiduoResponse atualizar(Long id, ResiduoRequest request) {
       Residuo residuo = residuoRepository.findById(id)
               .orElseThrow(() -> new ResiduoNotFoundException("Residuo não foi encontrado com o ID: " + id));

       residuoMapper.atualizarResiduo(request, residuo);

       Residuo salvo = residuoRepository.save(residuo);
       return residuoMapper.toResiduoResponse(salvo);
    }

    public void deletar(Long id) {
        residuoRepository.findById(id)
                .orElseThrow(() -> new ResiduoNotFoundException("Residuo não foi encontrado com o ID: " + id));
        residuoRepository.deleteById(id);
    }

    public ResiduoResponse buscarPorId(Long id) {
        Residuo residuo = residuoRepository.findById(id)
                .orElseThrow(() -> new ResiduoNotFoundException("Residuo não foi encontrado com o ID: " + id));
        return residuoMapper.toResiduoResponse(residuo);
    }

    @Transactional
    public ResiduoResponse avancarStatus(Long id, StatusResiduo novoStatus) {
        Residuo residuo = residuoRepository.findById(id)
                .orElseThrow(() -> new ResiduoNotFoundException("Residuo não foi encontrado com o ID: " + id));

        validarTransicao(residuo.getStatus(), novoStatus);

        residuo.setStatus(novoStatus);
        if (novoStatus == StatusResiduo.DESTRUIDO) {
            residuo.setDataDestinacao(LocalDateTime.now());
        }

        Residuo salvo = residuoRepository.save(residuo);
        return residuoMapper.toResiduoResponse(salvo);
    }

    private void validarTransicao(StatusResiduo atual, StatusResiduo novo) {
        boolean valido = switch (atual) {
            case ARMAZENADO -> novo == StatusResiduo.EM_TRATAMENTO;
            case EM_TRATAMENTO -> novo == StatusResiduo.DESTRUIDO;
            case DESTRUIDO -> false; // estado final, não pode mudar mais
        };

        if (!valido) {
            throw new TransicaoStatusInvalidaException(
                    "Não é possível mudar de " + atual + " para " + novo);
        }
    }



}
