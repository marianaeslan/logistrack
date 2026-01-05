package br.com.logistrack.dto.encomenda;

import br.com.logistrack.entity.StatusEncomenda;
import lombok.Data;

// Atualiza onde o pacote está
@Data
public class StatusUpdateDTO {
    private String novaLocalizacao;
    private StatusEncomenda novoStatus;


}
