package br.com.logistrack.dto.encomenda;

import br.com.logistrack.entity.Encomenda;
import br.com.logistrack.entity.StatusEncomenda;
import lombok.Data;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

// Atualiza onde o pacote está
@Data
public class StatusUpdateDTO {
    private String novaLocalizacao;
    private StatusEncomenda novoStatus;

}
