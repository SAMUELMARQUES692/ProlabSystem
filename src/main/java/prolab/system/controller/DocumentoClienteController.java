package prolab.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prolab.system.documentation.DocumentoClienteControllerDoc;
import prolab.system.request.DocumentoClienteRequest;
import prolab.system.response.DocumentoClienteResponse;
import prolab.system.service.DocumentoClienteService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/documentos")
public class DocumentoClienteController implements DocumentoClienteControllerDoc {

    private final DocumentoClienteService documentoClienteService;

    @PostMapping
    public ResponseEntity<DocumentoClienteResponse> cadastrar(@RequestBody @Valid DocumentoClienteRequest  request) {
        DocumentoClienteResponse response = documentoClienteService.cadastrar(request);
        URI location = URI.create("/api/documentos/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<DocumentoClienteResponse> atualizar(@PathVariable Long id, @RequestBody @Valid DocumentoClienteRequest request) {
        return ResponseEntity.ok(documentoClienteService.atualizar(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        documentoClienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<DocumentoClienteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(documentoClienteService.buscarPorId(id));
    }


}
