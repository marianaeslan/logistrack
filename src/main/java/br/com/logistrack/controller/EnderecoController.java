package br.com.logistrack.controller;

import br.com.logistrack.controller.doc.EnderecoControllerDoc;
import br.com.logistrack.dto.endereco.EnderecoInputDTO;
import br.com.logistrack.entity.Endereco;
import br.com.logistrack.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/endereco")
public class EnderecoController implements EnderecoControllerDoc {
    private final EnderecoService enderecoService;

    @PostMapping("/{idUsuario}")
    public ResponseEntity<EnderecoInputDTO> create (@PathVariable Long idUsuario,
                                                    @RequestBody EnderecoInputDTO enderecoInputDTO) {
        return ResponseEntity.ok(enderecoService.create(idUsuario, enderecoInputDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable long id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoInputDTO> update (@PathVariable long id,
                                                         @RequestBody EnderecoInputDTO enderecoInputDTO) {
        return ResponseEntity.ok(enderecoService.update(id, enderecoInputDTO));
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> list() {
        return ResponseEntity.ok(enderecoService.list());
    }

   @GetMapping("/{cep}")
    public ResponseEntity<Optional<Endereco>> getByCep (@PathVariable String cep) {
        return ResponseEntity.ok(enderecoService.getByCep(cep));
   }

}
