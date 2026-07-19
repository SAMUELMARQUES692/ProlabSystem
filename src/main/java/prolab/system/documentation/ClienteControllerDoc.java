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
import prolab.system.request.ClienteRequest;
import prolab.system.response.ClienteResponse;

import java.util.List;

@Tag(name = "Cliente", description = "Recurso responsavel pelo gerenciamento dos clientes na API")
public interface ClienteControllerDoc {

    @Operation(summary = "Salvar Cliente", description = "Metodo responsavel por cadastrar e salvar novos clientes no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Cliente salvo com sucesso", content = @Content(schema = @Schema(implementation = ClienteResponse.class)))
    ResponseEntity<ClienteResponse> cadastrar(@RequestBody @Valid ClienteRequest request);

    @Operation(summary = "Atualizar Cliente", description = "Metodo responsavel por atualizar clientes no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = @Content(schema = @Schema(implementation = ClienteResponse.class)))
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content())
    ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteRequest request);

    @Operation(summary = "Deleta Cliente por ID", description = "Metodo responsavel por deletar clientes pelo ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso", content = @Content())
    @ApiResponse(responseCode = "404", description = "Cliente não encontrada", content = @Content())
    ResponseEntity<Void> deletar(@PathVariable Long id);

    @Operation(summary = "Busca os Clientes pelo ID", description = "Metodo responsavel por buscar clientes usando o ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso", content = @Content(schema = @Schema(implementation = ClienteResponse.class)))
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content())
    ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Busca todos clientes", description = "Metodo responsavel por buscar todos os clientes cadastrados no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso", content = @Content(schema = @Schema(implementation = ClienteResponse.class)))
    ResponseEntity<List<ClienteResponse>> buscarTodos();

}
