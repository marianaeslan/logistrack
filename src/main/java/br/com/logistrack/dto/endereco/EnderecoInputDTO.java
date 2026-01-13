package br.com.logistrack.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

// Cadastra novo endereço
@Getter
@Setter
public class EnderecoInputDTO {

    @NotBlank(message = "O cep é obrigatório")
    String cep;

    @NotBlank(message = "O complemento é obrigatório")
    String complemento;

    @NotBlank(message = "O bairro é obrigatório")
    String bairro;

    @NotBlank(message = "O uf é obrigatório")
    String uf;
}
