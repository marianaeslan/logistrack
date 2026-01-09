package br.com.logistrack.dto.encomenda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

// Cadastra nova encomenda
@Getter
@Setter
public class EncomendaInputDTO {
    @NotBlank(message = "O remetente é obrigatório")
    String remetente;

    @NotBlank(message = "O destinatário é obrigatório")
    String destinatario;

    @NotNull(message = "O prazo de entrega é obrigatório")
    Integer prazoEntrega;

    @NotBlank(message = "O email é obrigatório")
    String email;

    @NotBlank(message = "O cep é obrigatório")
    String cep;

    @NotBlank(message = "O complemento é obrigatório")
    String complemento;


}
