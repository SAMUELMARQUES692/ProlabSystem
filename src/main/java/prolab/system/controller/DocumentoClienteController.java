package prolab.system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prolab.system.request.DocumentoClienteRequest;
import prolab.system.response.DocumentoClienteResponse;
import prolab.system.service.DocumentoClienteService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/documentos")
public class DocumentoClienteController {

    private final DocumentoClienteService documentoClienteService;

    @PostMapping
    public ResponseEntity<DocumentoClienteResponse> cadastrar(@RequestBody DocumentoClienteRequest  request) {
        DocumentoClienteResponse response = documentoClienteService.cadastrar(request);
        URI location = URI.create("/api/documentos" + response.id());
        return ResponseEntity.created(location).body(response);
    }
}
