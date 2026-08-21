package prolab.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prolab.system.entity.Palete;
import prolab.system.entity.PosicaoEstoque;
import prolab.system.entity.Recebimento;
import prolab.system.entity.Residuo;
import prolab.system.enums.StatusResiduo;
import prolab.system.enums.TipoResiduo;
import prolab.system.exception.*;
import prolab.system.mapper.ResiduoMapper;
import prolab.system.repository.PaleteRepository;
import prolab.system.repository.PosicaoEstoqueRepository;
import prolab.system.repository.RecebimentoRepository;
import prolab.system.repository.ResiduoRepository;
import prolab.system.request.ResiduoRequest;
import prolab.system.response.ResiduoResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ResiduoService {

    private final ResiduoRepository residuoRepository;
    private final ResiduoMapper residuoMapper;
    private final PosicaoEstoqueRepository posicaoEstoqueRepository;
    private final PaleteRepository paleteRepository;


    @Transactional
    public ResiduoResponse cadastrar(ResiduoRequest request) {
        Palete palete = paleteRepository.findById(request.paleteId())
                .orElseThrow(() -> new PaleteNotFoundException("Palete não encontrado com ID: " + request.paleteId()));

        PosicaoEstoque posicao = posicaoEstoqueRepository.findById(request.posicaoId())
                .orElseThrow(() -> new PosicaoNotFoundException("Posição não encontrada com o ID: " + request.posicaoId()));

        BigDecimal quantidadeAtual  = residuoRepository.somarPesoPorPosicao(request.posicaoId());
        BigDecimal novaQuantidade = quantidadeAtual.add(palete.getPeso());

        if (posicao.getCapacidade() != null && posicao.getCapacidade().compareTo(novaQuantidade) < 0) {
            throw new CapacidadeExcedidaException(
                    "Capacidade da posição excedida. Capacidade: " + posicao.getCapacidade() +
                            ", quantidade após adicionar: " + novaQuantidade
            );
        }

        Residuo residuo = residuoMapper.toResiduo(request);
        residuo.setPalete(palete);
        residuo.setPosicaoEstoque(posicao);
        residuo.setStatus(StatusResiduo.ARMAZENADO);

        Residuo salvo = residuoRepository.save(residuo);
        return residuoMapper.toResiduoResponse(salvo);
    }

    @Transactional
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

    public List<ResiduoResponse> buscarPorTipoResiduo(TipoResiduo tipo) {
        return residuoRepository.findByPaleteTipo(tipo).stream()
                .map(residuoMapper::toResiduoResponse)
                .toList();
    }

    public List<ResiduoResponse> buscarPorPosicaoId(Long posicaoId) {
        posicaoEstoqueRepository.findById(posicaoId)
                .orElseThrow(() -> new PosicaoNotFoundException("Posição Não encontrada com o ID: " + posicaoId));

       return residuoRepository.findByPosicaoEstoqueId(posicaoId).stream()
               .map(residuoMapper::toResiduoResponse)
               .toList();
    }

    public List<ResiduoResponse> buscarPorStatusResiduo(StatusResiduo status) {
        return residuoRepository.findByStatus(status).stream()
                .map(residuoMapper::toResiduoResponse)
                .toList();
    }

    public BigDecimal calculoTotalPesoPorPosicao(Long posicaoId) {
        if (posicaoEstoqueRepository.findById(posicaoId).isEmpty()) {
            throw new PosicaoNotFoundException("Posição não encontrada com o ID: " + posicaoId);
        }
        return residuoRepository.somarPesoPorPosicao(posicaoId);
    }



}
