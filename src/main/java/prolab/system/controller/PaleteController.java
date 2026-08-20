package prolab.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prolab.system.request.PaleteRequest;
import prolab.system.response.PaleteResponse;
import prolab.system.service.PaleteService;

import java.net.URI;

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

}
