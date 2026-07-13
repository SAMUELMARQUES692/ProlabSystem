package prolab.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prolab.system.request.AtualizarStatusRequest;
import prolab.system.request.ResiduoRequest;
import prolab.system.response.ResiduoResponse;
import prolab.system.service.ResiduoService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/residuos")
public class ResiduoController {

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

}
