package prolab.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prolab.system.documentation.RecebimentoControllerDoc;
import prolab.system.request.RecebimentoRequest;
import prolab.system.response.RecebimentoResponse;
import prolab.system.service.RecebimentoService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recebimentos")
public class RecebimentoController implements RecebimentoControllerDoc {

    private final RecebimentoService recebimentoService;

    @PostMapping
    public ResponseEntity<RecebimentoResponse> cadastrar(@RequestBody @Valid RecebimentoRequest request) {
        RecebimentoResponse response = recebimentoService.cadastrar(request);
        URI location = URI.create("/api/recebimento/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<RecebimentoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid RecebimentoRequest request) {
        return ResponseEntity.ok(recebimentoService.atualizar(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        recebimentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
