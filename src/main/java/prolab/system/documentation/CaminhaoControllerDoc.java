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
import prolab.system.request.CaminhaoRequest;
import prolab.system.response.AgendamentoResponse;
import prolab.system.response.CaminhaoResponse;

import java.util.List;

@Tag(name = "Caminhao", description = "Recurso responsavel pelo gerenciamento dos veiculos na API")
public interface CaminhaoControllerDoc {


    @Operation(summary = "Salvar Veiculo", description = "Metodo responsavel por cadastrar e salvar novos veiculos no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Veiculo salvo com sucesso", content = @Content(schema = @Schema(implementation = CaminhaoResponse.class)))
    ResponseEntity<CaminhaoResponse> cadastrar(@RequestBody @Valid CaminhaoRequest request);

    @Operation(summary = "Atualizar Veiculo", description = "Metodo responsavel por atualizar veiculos no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Veiculo atualizado com sucesso", content = @Content(schema = @Schema(implementation = CaminhaoResponse.class)))
    @ApiResponse(responseCode = "404", description = "Veiculo não encontrado", content = @Content())
    ResponseEntity<CaminhaoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid CaminhaoRequest request);

    @Operation(summary = "Deleta Veiculos por ID", description = "Metodo responsavel por deletar veiculos pelo ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Veiculo deletado com sucesso", content = @Content())
    @ApiResponse(responseCode = "404", description = "Veiculo não encontrada", content = @Content())
    ResponseEntity<Void> deletar(@PathVariable Long id);

    @Operation(summary = "Busca todos veiculos", description = "Metodo responsavel por buscar todos os veiculos cadastrados no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Veiulos encontrados com sucesso", content = @Content(schema = @Schema(implementation = AgendamentoResponse.class)))
    ResponseEntity<List<CaminhaoResponse>> buscarTodos();

}
