package br.com.logistrack.dto.usuario;

import br.com.logistrack.entity.enums.TipoCargo;

public record RegisterDTO(String email, String senha,  TipoCargo cargo) {
}
