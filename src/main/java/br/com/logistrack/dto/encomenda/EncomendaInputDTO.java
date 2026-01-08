package br.com.logistrack.dto.encomenda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// Cadastra nova encomenda
@Data
public class EncomendaInputDTO {
    @NotBlank(message = "O remetente é obrigatório")
    String remetente;

    @NotBlank(message = "O destinatário é obrigatório")
    String destinatario;

    @NotNull(message = "O prazo de entrega é obrigatório")
    Integer prazoEntrega;

    @NotBlank(message = "O email é obrigatório")
    String email;


}
