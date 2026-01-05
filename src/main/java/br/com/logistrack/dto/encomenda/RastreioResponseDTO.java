package br.com.logistrack.dto.encomenda;

import lombok.Data;

// Saída para o usuário
@Data
public class RastreioResponseDTO {
    private String codigoRastreio;
    private String statusAtual;
    private String localizacaoAtual;
    private long tempoEmTransito;
    private boolean atrasado;
}
