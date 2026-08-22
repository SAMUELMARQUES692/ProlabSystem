package prolab.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prolab.system.request.PaleteRequest;
import prolab.system.response.PaleteResponse;
import prolab.system.service.PaleteService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/paletes")
public class PaleteController {

    private final PaleteService paleteService;

    @PostMapping
    public ResponseEntity<PaleteResponse> cadastrar(@RequestBody @Valid PaleteRequest request) {
        PaleteResponse response = paleteService.cadastrar(request);
        URI location = URI.create("/api/paletes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PaleteResponse>> buscartodos() {
        return ResponseEntity.ok(paleteService.buscarTodos());
    }

    @GetMapping("{prime}/prime")
    public ResponseEntity<List<PaleteResponse>> buscarPorPrime(@PathVariable String prime) {
        return ResponseEntity.ok(paleteService.buscarPorPrime(prime));
    }

}
