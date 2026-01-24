package br.com.logistrack.dto.encomenda;

import br.com.logistrack.entity.enums.StatusEncomenda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;



// Saída para o usuário
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RastreioResponseDTO {
    private String codigoRastreio;
    private String statusAtual;
    private String localizacaoAtual;
    private long tempoEmTransito;
}

