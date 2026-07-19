package prolab.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prolab.system.documentation.AgendamentoControllerDoc;
import prolab.system.enums.StatusAgendamento;
import prolab.system.request.AgendamentoRequest;
import prolab.system.response.AgendamentoResponse;
import prolab.system.service.AgendamentoService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController implements AgendamentoControllerDoc {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponse> cadastrar(@RequestBody @Valid AgendamentoRequest request) {
        AgendamentoResponse response = agendamentoService.cadastrar(request);
        URI location = URI.create("/api/agendamentos/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<AgendamentoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid AgendamentoRequest request) {
        return ResponseEntity.ok(agendamentoService.atualizar(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AgendamentoResponse>> buscarPorclienteId(@PathVariable Long clienteId) {
        return ResponseEntity.ok(agendamentoService.buscarPorCliente(clienteId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AgendamentoResponse>> buscarPorStauts(@PathVariable StatusAgendamento status) {
        return ResponseEntity.ok(agendamentoService.buscarPorStatus(status));
    }







}
