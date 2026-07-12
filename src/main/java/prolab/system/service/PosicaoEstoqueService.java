package prolab.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prolab.system.domain.PosicaoEstoque;
import prolab.system.exception.PosicaoJaCadastradaException;
import prolab.system.exception.PosicaoNotFoundException;
import prolab.system.mapper.PosicaoEstoqueMapper;
import prolab.system.repository.PosicaoEstoqueRepository;
import prolab.system.request.PosicaoEstoqueRequest;
import prolab.system.response.PosicaoEstoqueResponse;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PosicaoEstoqueService {

    private final PosicaoEstoqueRepository posicaoEstoqueRepository;
    private final PosicaoEstoqueMapper posicaoEstoqueMapper;

    public PosicaoEstoqueResponse cadastrar(PosicaoEstoqueRequest request) {
        if (posicaoEstoqueRepository.findByCodigo(request.codigo()).isPresent()) {
            throw new PosicaoJaCadastradaException("Posição já existe no banco de dados");
        }

            PosicaoEstoque novaPosicao = posicaoEstoqueMapper.toPosicaoEstoque(request);
            PosicaoEstoque salvar = posicaoEstoqueRepository.save(novaPosicao);
            return posicaoEstoqueMapper.toPosicaoEstoqueResponse(salvar);
    }

    public PosicaoEstoqueResponse atualizar(Long id, PosicaoEstoqueRequest request) {
        PosicaoEstoque posicao = posicaoEstoqueRepository.findById(id)
                .orElseThrow(() -> new PosicaoNotFoundException("Posição não encontrada com o ID: " + id));

        posicaoEstoqueMapper.atualizarPosicaoEstoque(request, posicao);

        PosicaoEstoque salvar = posicaoEstoqueRepository.save(posicao);
        return posicaoEstoqueMapper.toPosicaoEstoqueResponse(salvar);
    }

    public void deletar(Long id) {
        posicaoEstoqueRepository.findById(id)
                .orElseThrow(() -> new PosicaoNotFoundException("Posição não encontrada com o ID: " + id));
        posicaoEstoqueRepository.deleteById(id);
    }

    public List<PosicaoEstoqueResponse> buscarTodas() {
        return posicaoEstoqueRepository.findAll().stream()
                .map(posicaoEstoqueMapper::toPosicaoEstoqueResponse)
                .toList();
    }

}
