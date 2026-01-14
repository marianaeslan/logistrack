package br.com.logistrack.controller;

import br.com.logistrack.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {

    // TODO: implementar Usuario Controller
    private final UsuarioService usuarioService;
}
