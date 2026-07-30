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
import org.springframework.web.bind.annotation.RequestParam;
import prolab.system.enums.StatusPosicao;
import prolab.system.request.PosicaoEstoqueRequest;
import prolab.system.response.PosicaoEstoqueResponse;
import prolab.system.response.ResiduoResponse;

import java.util.List;

@Tag(name = "Posição Estoque", description = "Recurso responsavel pelo gerenciamento das posições do estoque na API")
public interface PosicaoEstoqueControllerDoc {

    @Operation(summary = "Salvar Posição Estoque", description = "Metodo responsavel por cadastrar e salvar novas posições no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Posição salva com sucesso", content = @Content(schema = @Schema(implementation = PosicaoEstoqueResponse.class)))
    ResponseEntity<PosicaoEstoqueResponse> cadastrar(@RequestBody @Valid PosicaoEstoqueRequest request);

    @Operation(summary = "Atualizar Posição Estoque", description = "Metodo responsavel por atualizar posições no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Posição atualizada com sucesso", content = @Content(schema = @Schema(implementation = PosicaoEstoqueResponse.class)))
    @ApiResponse(responseCode = "404", description = "Posição não encontrada", content = @Content())
    ResponseEntity<PosicaoEstoqueResponse> atualizar(@PathVariable Long id, @RequestBody @Valid PosicaoEstoqueRequest request);

    @Operation(summary = "Deleta Posições por ID", description = "Metodo responsavel por deletar posições pelo ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Posição deletada com sucesso", content = @Content())
    @ApiResponse(responseCode = "404", description = "Posição não encontrada", content = @Content())
    ResponseEntity<Void> deletar(@PathVariable Long id);

    @Operation(summary = "Busca todas Posições", description = "Metodo responsavel por buscar todas as posições cadastradas no banco de dados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Posições encontradas com sucesso", content = @Content(schema = @Schema(implementation = PosicaoEstoqueResponse.class)))
    ResponseEntity<List<PosicaoEstoqueResponse>> buscarTodas();

    @Operation(summary = "Busca Residuos no estoque pelo codigo", description = "Metodo responsavel por buscar Residuos alocaldos em uma posição no estoque, usando o codigo da posicão",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Posição encontrada com sucesso", content = @Content())
    @ApiResponse(responseCode = "404", description = "Posição não encontrada", content = @Content())
    ResponseEntity<List<ResiduoResponse>> buscarPorCodigo(@PathVariable String codigo);

    @Operation(summary = "Busca Residuos no estoque pelo status", description = "Metodo responsavel por buscar posições usando o status",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Posição encontrada com sucesso", content = @Content())
    @ApiResponse(responseCode = "404", description = "Posição não encontrada", content = @Content())
    ResponseEntity<List<PosicaoEstoqueResponse>> buscarPosicaoPorStatus(@RequestParam StatusPosicao status);
}
