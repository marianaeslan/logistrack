package br.com.logistrack.entity;

import lombok.Data;
import lombok.Getter;

@Getter
public enum StatusEncomenda {
    EM_PROCESSAMENTO("Em processamento"),
    POSTADO("Postado"),
    EM_TRANSITO("Em trânsito"),
    ATRASADO("Atrasado"),
    ENTREGUE("Entregue");

    private final String descricao;

    StatusEncomenda(String descricao) {
        this.descricao = descricao;
    }

}
