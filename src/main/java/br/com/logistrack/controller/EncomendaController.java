package br.com.logistrack.controller;

import br.com.logistrack.controller.doc.EncomendaControllerDoc;
import br.com.logistrack.dto.encomenda.EncomendaInputDTO;
import br.com.logistrack.dto.encomenda.RastreioResponseDTO;
import br.com.logistrack.dto.encomenda.StatusUpdateDTO;
import br.com.logistrack.entity.Encomenda;
import br.com.logistrack.service.EmailService;
import br.com.logistrack.service.EncomendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/encomenda")
public class EncomendaController implements EncomendaControllerDoc {
    private final EncomendaService encomendaService;

    @PostMapping("/{idUsuario}")
    public ResponseEntity<EncomendaInputDTO> create (@PathVariable Long idUsuario, @Valid @RequestBody EncomendaInputDTO encomendaInputDTO) {
        return new ResponseEntity<>(encomendaService.create(idUsuario, encomendaInputDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<StatusUpdateDTO> statusUpdate (@PathVariable long id,
                                                         @Valid @RequestBody StatusUpdateDTO statusUpdateDTO) {
        StatusUpdateDTO encomenda = encomendaService.update(id, statusUpdateDTO);

        return new ResponseEntity<>(encomenda, HttpStatus.OK);
    }

    @GetMapping("/{codigoRastreio}")
    public ResponseEntity<Optional<RastreioResponseDTO>> findByRastreio (@PathVariable String codigoRastreio) {
        return new ResponseEntity<>(encomendaService.findByRastreio(codigoRastreio), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable long id) {
        encomendaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
