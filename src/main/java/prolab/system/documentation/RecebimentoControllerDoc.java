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
import prolab.system.request.RecebimentoRequest;
import prolab.system.response.RecebimentoResponse;

@Tag(name = "Recebimento", description = "Recurso responsavel pelo gerenciamento dos recebimentos na API")
public interface RecebimentoControllerDoc {

    @Operation(summary = "Salvar Recebimento", description = "Metodo responsavel por cadastrar e salvar novos recebimentos no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Recebimento salvo com sucesso", content = @Content(schema = @Schema(implementation = RecebimentoResponse.class)))
    ResponseEntity<RecebimentoResponse> cadastrar(@RequestBody @Valid RecebimentoRequest request);

    @Operation(summary = "Atualizar Recebimento", description = "Metodo responsavel por atualizar recebimentos no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Recebimento atualizado com sucesso", content = @Content(schema = @Schema(implementation = RecebimentoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Recebimento não encontrado", content = @Content())
    ResponseEntity<RecebimentoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid RecebimentoRequest request);

    @Operation(summary = "Deleta Recebimento por ID", description = "Metodo responsavel por deletar recebimentos pelo ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Recebimento deletado com sucesso", content = @Content())
    @ApiResponse(responseCode = "404", description = "Recebimento não encontrado", content = @Content())
    ResponseEntity<Void> deletar(@PathVariable Long id);

}
