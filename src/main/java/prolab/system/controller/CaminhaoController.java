package prolab.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prolab.system.request.CaminhaoRequest;
import prolab.system.response.CaminhaoResponse;
import prolab.system.service.CaminhaoService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/caminhoes")
public class CaminhaoController {

    private final CaminhaoService caminhaoService;

    @PostMapping
    public ResponseEntity<CaminhaoResponse> cadastrar(@RequestBody @Valid CaminhaoRequest request) {
        CaminhaoResponse response = caminhaoService.cadastrar(request);
        URI location = URI.create("/api/caminhoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaminhaoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid CaminhaoRequest request) {
        return ResponseEntity.ok(caminhaoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        caminhaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CaminhaoResponse>> buscarTodos() {
        return ResponseEntity.ok(caminhaoService.buscarTodos());
    }





}
