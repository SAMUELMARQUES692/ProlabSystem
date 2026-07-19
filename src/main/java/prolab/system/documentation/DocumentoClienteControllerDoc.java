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
import prolab.system.request.DocumentoClienteRequest;
import prolab.system.response.DocumentoClienteResponse;

@Tag(name = "Documentos Clientes", description = "Recurso responsavel pelo gerenciamento dos documentos dos clientes na API")
public interface DocumentoClienteControllerDoc {

    @Operation(summary = "Salvar Documento", description = "Metodo responsavel por cadastrar e salvar novos documentos no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Documento salvo com sucesso", content = @Content(schema = @Schema(implementation = DocumentoClienteResponse.class)))
    ResponseEntity<DocumentoClienteResponse> cadastrar(@RequestBody @Valid DocumentoClienteRequest request);

    @Operation(summary = "Atualizar Documento", description = "Metodo responsavel por atualizar documentos no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Cliente Documento com sucesso", content = @Content(schema = @Schema(implementation = DocumentoClienteResponse.class)))
    @ApiResponse(responseCode = "404", description = "Documento não encontrado", content = @Content())
    ResponseEntity<DocumentoClienteResponse> atualizar(@PathVariable Long id, @RequestBody @Valid DocumentoClienteRequest request);

    @Operation(summary = "Deletar Documento por ID", description = "Metodo responsavel por deletar documentos pelo ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Documento deletado com sucesso", content = @Content())
    @ApiResponse(responseCode = "404", description = "Documento não encontrada", content = @Content())
    ResponseEntity<Void> deletar(@PathVariable Long id);

    @Operation(summary = "Buscar Documentos pelo ID", description = "Metodo responsavel por buscar documentos usando o ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Documento encontrado com sucesso", content = @Content(schema = @Schema(implementation = DocumentoClienteResponse.class)))
    @ApiResponse(responseCode = "404", description = "Documento não encontrado", content = @Content())
    ResponseEntity<DocumentoClienteResponse> buscarPorId(@PathVariable Long id);
}
