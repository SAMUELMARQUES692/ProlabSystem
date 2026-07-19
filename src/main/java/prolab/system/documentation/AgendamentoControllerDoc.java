package prolab.system.documentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import prolab.system.enums.StatusAgendamento;
import prolab.system.request.AgendamentoRequest;
import prolab.system.response.AgendamentoResponse;

import java.util.List;

@Tag(name = "Agendamentos", description = "Recurso responsavel pelo gerenciamento dos agendamentos na API")
public interface AgendamentoControllerDoc {

    @Operation(summary = "Salvar Agendamento", description = "Metodo responsavel por cadastrar e salvar novos agendamentos no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Agendamento salvo com sucesso", content = @Content(schema = @Schema(implementation = AgendamentoResponse.class)))
    ResponseEntity<AgendamentoResponse> cadastrar(@RequestBody @Valid AgendamentoRequest request);

    @Operation(summary = "Atualizar Agendamento", description = "Metodo responsavel por atualizar agendamentos no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso", content = @Content(schema = @Schema(implementation = AgendamentoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content())
    ResponseEntity<AgendamentoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid AgendamentoRequest request);

    @Operation(summary = "Deleta Agendamentos por ID", description = "Metodo responsavel por deletar agendamentos pelo ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Agendamento deletado com sucesso", content = @Content())
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrada", content = @Content())
    ResponseEntity<Void> deletar(@PathVariable Long id);

    @Operation(summary = "Busca os Agendamentos pelo cliente ID", description = "Metodo responsavel por buscar agendamentos usando o ID do cliente",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Agendamento encontrado com sucesso", content = @Content(schema = @Schema(implementation = AgendamentoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content())
    ResponseEntity<List<AgendamentoResponse>> buscarPorclienteId(@PathVariable Long clienteId);

    @Operation(summary = "Busca os Agendamentos pelo status", description = "Metodo responsavel por buscar agendamentos usando o status",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Agendamento encontrado com sucesso", content = @Content(schema = @Schema(implementation = AgendamentoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content())
    ResponseEntity<List<AgendamentoResponse>> buscarPorStauts(@PathVariable StatusAgendamento status);


}
