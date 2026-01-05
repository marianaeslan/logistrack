package br.com.logistrack.entity;

public enum StatusEncomenda {
    EM_PROCESSAMENTO("Em processamento"),
    POSTADO("Postado"),
    EM_TRANSITO("Em trânsito"),
    ENTREGUE("Entregue");

    private final String descricao;

    StatusEncomenda(String descricao) {
        this.descricao = descricao;
    }

}
