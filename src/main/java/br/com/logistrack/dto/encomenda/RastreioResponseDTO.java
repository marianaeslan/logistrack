package br.com.logistrack.dto.encomenda;

import br.com.logistrack.entity.Encomenda;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

// Saída para o usuário
@Data
public class RastreioResponseDTO {
    private String codigoRastreio;
    private String statusAtual;
    private String localizacaoAtual;
    private long tempoEmTransito;
}

