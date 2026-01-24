package br.com.logistrack.dto.encomenda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// Cadastra nova encomenda
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EncomendaInputDTO {
    @NotBlank(message = "O remetente é obrigatório")
    String remetente;

    @NotBlank(message = "O destinatário é obrigatório")
    String destinatario;

    @NotNull(message = "O prazo de entrega é obrigatório")
    Integer prazoEntrega;

}
