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
import prolab.system.request.AtualizarStatusRequest;
import prolab.system.request.ResiduoRequest;
import prolab.system.response.ResiduoResponse;

@Tag(name = "Resíduos", description = "Recurso responsavel pelo gerenciamento dos resíduos na API")
public interface ResiduoControllerDoc {

    @Operation(summary = "Salvar Residuo", description = "Metodo responsavel por cadastrar e salvar novos residuos no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Residuo salvo com sucesso", content = @Content(schema = @Schema(implementation = ResiduoResponse.class)))
     ResponseEntity<ResiduoResponse> cadastrar(@RequestBody @Valid ResiduoRequest request);

    @Operation(summary = "Atualizar Residuo", description = "Metodo responsavel por atualizar residuos no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Residuo atualizado com sucesso", content = @Content(schema = @Schema(implementation = ResiduoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Residuo não encontrado", content = @Content())
     ResponseEntity<ResiduoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ResiduoRequest request);

    @Operation(summary = "Deleta Residuo por ID", description = "Metodo responsavel por deletar residuos pelo ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Residuo deletado com sucesso", content = @Content())
    @ApiResponse(responseCode = "404", description = "Residuo não encontrado", content = @Content())
     ResponseEntity<Void> deletar(@PathVariable Long id);

    @Operation(summary = "Busca os Residuos pelo ID", description = "Metodo responsavel por buscar residuos usando o ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Residuo encontrado com sucesso", content = @Content(schema = @Schema(implementation = ResiduoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Residuo não encontrado", content = @Content())
     ResponseEntity<ResiduoResponse> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Atualizar status do residuo", description = "Metodo responsavel por avançar o status do residuo usando o ID e status",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Residuo avançado com sucesso", content = @Content(schema = @Schema(implementation = ResiduoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Residuo não encontrado", content = @Content())
     ResponseEntity<ResiduoResponse> atualizarStatus(@PathVariable Long id, @RequestBody @Valid AtualizarStatusRequest request);

}
