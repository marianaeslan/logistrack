package br.com.logistrack.entity.enums;

import lombok.Getter;

@Getter
public enum TipoCargo {
    ADMIN(1),
    USER(2),
    MANAGER(3);

    private final int tipo;

    TipoCargo(int tipo) {
        this.tipo = tipo;
    }
}
