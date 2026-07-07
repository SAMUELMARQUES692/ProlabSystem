package prolab.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import prolab.system.domain.Caminhao;
import prolab.system.exception.CaminhaoNotFoundException;
import prolab.system.mapper.CaminhaoMapper;
import prolab.system.repository.CaminhaoRepository;
import prolab.system.request.CaminhaoRequest;
import prolab.system.response.CaminhaoResponse;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CaminhaoService {

    private final CaminhaoRepository caminhaoRepository;
    private final CaminhaoMapper caminhaoMapper;

    public CaminhaoResponse cadastrar(CaminhaoRequest request) {

        if (caminhaoRepository.findByPlaca(request.placa()).isPresent()) {
            throw new DataIntegrityViolationException("Caminhão com a placa " + request.placa() + " já cadastrado");
        }

        Caminhao newCaminhao = caminhaoMapper.toCaminhao(request);
        Caminhao save = caminhaoRepository.save(newCaminhao);
        return caminhaoMapper.toCaminhaoResponse(save);
    }

    public CaminhaoResponse atualizar(Long id, CaminhaoRequest request) {
        Caminhao caminhao = caminhaoRepository.findById(id)
                .orElseThrow(() -> new CaminhaoNotFoundException("Caminhão não encontrado"));

        caminhaoMapper.atualizarCaminhao(request, caminhao);

        Caminhao salvo = caminhaoRepository.save(caminhao);
        return caminhaoMapper.toCaminhaoResponse(salvo);
    }

    public void deletar(Long id) {
        caminhaoRepository.findById(id)
                .orElseThrow(() -> new CaminhaoNotFoundException("Caminhão não encontrado"));
        caminhaoRepository.deleteById(id);
    }

    public List<CaminhaoResponse> buscarTodos() {
        return caminhaoRepository.findAll().stream()
                .map(caminhaoMapper::toCaminhaoResponse)
                .toList();
    }
}
