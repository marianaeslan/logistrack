package br.com.logistrack.dto.encomenda;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

// Cadastra nova encomenda
@Data
public class EncomendaInputDTO {
    @NotBlank(message = "O remetente é obrigatório")
    String remetente;

    @NotBlank(message = "O destinatário é obrigatório")
    String destinatario;
}
