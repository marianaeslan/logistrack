package br.com.logistrack.dto.encomenda;

import br.com.logistrack.entity.enums.StatusEncomenda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// Atualiza onde o pacote está
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class StatusUpdateDTO {
    private String novaLocalizacao;
    private StatusEncomenda novoStatus;

}
