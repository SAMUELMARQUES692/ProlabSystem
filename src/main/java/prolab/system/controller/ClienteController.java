package prolab.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prolab.system.request.ClienteRequest;
import prolab.system.response.ClienteResponse;
import prolab.system.service.ClienteService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@RequestBody @Valid ClienteRequest request) {
        ClienteResponse response = clienteService.cadastrar(request);
        URI location = URI.create("/api/clientes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

}
