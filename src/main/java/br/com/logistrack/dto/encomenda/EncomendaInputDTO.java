package br.com.logistrack.dto.encomenda;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// Cadastra nova encomenda
@Data
public class EncomendaInputDTO {
    @NotBlank(message = "O remetente é obrigatório")
    String remetente;

    @NotBlank(message = "O destinatário é obrigatório")
    String destinatario;


}
