package br.com.logistrack.dto.endereco;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EnderecoResponseDTO {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
}
