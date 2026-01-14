package br.com.logistrack.controller.doc;

import br.com.logistrack.dto.encomenda.EncomendaInputDTO;
import br.com.logistrack.dto.encomenda.RastreioResponseDTO;
import br.com.logistrack.dto.encomenda.StatusUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface EncomendaControllerDoc {
    @Tag(name = "Consultas | Encomendas", description = "Gerenciamento de encomendas")
    @Operation(summary = "Criar encomenda", description = "Cria uma nova encomenda")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
            }
    )
    public ResponseEntity<EncomendaInputDTO> create (@PathVariable Long idUsuario,
                                                     @Valid @RequestBody EncomendaInputDTO encomendaInputDTO);

    @Tag(name = "Consultas | Encomendas", description = "Gerenciamento de encomendas")
    @Operation(summary = "Atualizar status da encomenda", description = "Atualiza o status de uma encomenda")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
            }
    )
    public ResponseEntity<StatusUpdateDTO> statusUpdate (@PathVariable long id,
                                                         @Valid @RequestBody StatusUpdateDTO statusUpdateDTO);

    @Tag(name = "Consultas | Encomendas", description = "Gerenciamento de encomendas")
    @Operation(summary = "Buscar encomenda por código de rastreio", description = "Busca uma encomenda por código de rastreio")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
            }
    )
    public ResponseEntity<Optional<RastreioResponseDTO>> findByRastreio (@PathVariable String codigoRastreio);

    @Tag(name = "Consultas | Encomendas", description = "Gerenciamento de encomendas")
    @Operation(summary = "Deletar encomenda por id", description = "Deleta uma encomenda por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
            }
    )
    public ResponseEntity<Void> delete (@PathVariable long id);
}
