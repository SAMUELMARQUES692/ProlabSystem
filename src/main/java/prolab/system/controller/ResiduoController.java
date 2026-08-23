package prolab.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prolab.system.documentation.ResiduoControllerDoc;
import prolab.system.enums.StatusResiduo;
import prolab.system.enums.TipoResiduo;
import prolab.system.request.AtualizarStatusRequest;
import prolab.system.request.ResiduoRequest;
import prolab.system.response.ResiduoResponse;
import prolab.system.service.ResiduoService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/residuos")
public class ResiduoController implements ResiduoControllerDoc {

    private final ResiduoService residuoService;

    @PostMapping
    public ResponseEntity<ResiduoResponse> cadastrar(@RequestBody @Valid ResiduoRequest request) {
        ResiduoResponse response = residuoService.cadastrar(request);
        URI location = URI.create("/api/residuos/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResiduoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ResiduoRequest request) {
        return ResponseEntity.ok(residuoService.atualizar(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        residuoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResiduoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(residuoService.buscarPorId(id));
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<ResiduoResponse> atualizarStatus(@PathVariable Long id, @RequestBody @Valid AtualizarStatusRequest request) {
        return ResponseEntity.ok(residuoService.avancarStatus(id, request.novoStatus()));
    }

    @GetMapping("{tipoResiduo}/tipo")
    public ResponseEntity<List<ResiduoResponse>> buscarPorTipoResiduo(@PathVariable TipoResiduo tipoResiduo) {
        return ResponseEntity.ok(residuoService.buscarPorTipoResiduo(tipoResiduo));
    }

    @GetMapping("{posicaoId}/posicao")
    public ResponseEntity<List<ResiduoResponse>> buscarResiduoPorPosicao(@PathVariable Long posicaoId) {
        return ResponseEntity.ok(residuoService.buscarPorPosicaoId(posicaoId));
    }

    @GetMapping("/status-residuo")
    public ResponseEntity<List<ResiduoResponse>> buscarPorStatusResiduo(@RequestParam StatusResiduo status) {
        return ResponseEntity.ok(residuoService.buscarPorStatusResiduo(status));
    }

    @GetMapping("{codigo}/codigo")
    public ResponseEntity<List<ResiduoResponse>> buscarPorCodigoPosicao(@PathVariable String codigo) {
        return ResponseEntity.ok(residuoService.buscarPorCodigoPosicao(codigo));
    }

    @GetMapping
    public ResponseEntity<List<ResiduoResponse>> buscarTodos() {
        return ResponseEntity.ok(residuoService.buscarTodos());
    }



}
