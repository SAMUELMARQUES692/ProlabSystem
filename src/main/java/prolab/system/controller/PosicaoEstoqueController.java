package prolab.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prolab.system.request.PosicaoEstoqueRequest;
import prolab.system.response.PosicaoEstoqueResponse;
import prolab.system.service.PosicaoEstoqueService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posicoes")
public class PosicaoEstoqueController {

    private final PosicaoEstoqueService posicaoEstoqueService;

    @PostMapping
    public ResponseEntity<PosicaoEstoqueResponse> cadastrar(@RequestBody @Valid PosicaoEstoqueRequest request) {
        PosicaoEstoqueResponse response = posicaoEstoqueService.cadastrar(request);
        URI location = URI.create("/api/posicoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<PosicaoEstoqueResponse> atualizar(@PathVariable Long id, @RequestBody @Valid PosicaoEstoqueRequest request) {
        return ResponseEntity.ok(posicaoEstoqueService.atualizar(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        posicaoEstoqueService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PosicaoEstoqueResponse>> buscarTodas() {
        return ResponseEntity.ok(posicaoEstoqueService.buscarTodas());
    }
}
