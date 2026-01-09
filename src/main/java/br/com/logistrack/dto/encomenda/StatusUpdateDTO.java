package br.com.logistrack.dto.encomenda;

import br.com.logistrack.entity.enums.StatusEncomenda;
import lombok.Getter;
import lombok.Setter;

// Atualiza onde o pacote está
@Getter
@Setter
public class StatusUpdateDTO {
    private String novaLocalizacao;
    private StatusEncomenda novoStatus;

}
