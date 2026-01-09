package br.com.logistrack.dto.encomenda;

import lombok.Getter;
import lombok.Setter;



// Saída para o usuário
@Getter
@Setter
public class RastreioResponseDTO {
    private String codigoRastreio;
    private String statusAtual;
    private String localizacaoAtual;
    private long tempoEmTransito;
}

