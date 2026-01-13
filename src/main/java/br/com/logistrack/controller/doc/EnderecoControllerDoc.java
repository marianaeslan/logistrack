package br.com.logistrack.controller.doc;

import br.com.logistrack.dto.endereco.EnderecoInputDTO;
import br.com.logistrack.entity.Endereco;
import br.com.logistrack.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface EnderecoControllerDoc {
    @Tag(name = "Consultas | Endereço", description = "Gerenciamento de endereços")
    @Operation(summary = "Criar endereço", description = "Cria um novo endereço")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
            }
    )
    public ResponseEntity<EnderecoInputDTO> create (@Valid @RequestBody EnderecoInputDTO enderecoInputDTO);

    @Tag(name = "Consultas | Endereço", description = "Gerenciamento de endereços")
    @Operation(summary = "Atualizar endereço", description = "Atualiza um endereço")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
            }
    )
    public ResponseEntity<EnderecoInputDTO> update (@PathVariable long id,
                                                         @Valid @RequestBody EnderecoInputDTO enderecoInputDTO);

    @Tag(name = "Consultas | Endereço", description = "Gerenciamento de endereços")
    @Operation(summary = "Buscar endereço por código da encomenda", description = "Busca endereço por código da encomenda")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
            }
    )
    public ResponseEntity<Endereco> findEnderecoByEncomendaId (@PathVariable Long id);

    @Tag(name = "Consultas | Endereço", description = "Gerenciamento de endereços")
    @Operation(summary = "Deletar endereço por id", description = "Deleta um endereço por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
            }
    )
    public ResponseEntity<Void> delete (@PathVariable long id);

    @Tag(name = "Consultas | Endereço", description = "Gerenciamento de endereços")
    @Operation(summary = "Consultar endereço por cep", description = "Busca um endereço por cep")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
            }
    )
    public ResponseEntity<Optional<Endereco>> getByCep (@RequestParam String cep);
}
