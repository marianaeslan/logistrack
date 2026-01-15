package br.com.logistrack.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCreateDTO {
    @NotBlank(message = "O email é obrigatório")
    String email;

    @NotBlank(message = "A senha é obrigatória")
    String senha;

    @NotBlank(message = "O cep é obrigatório")
    String cep;
}
