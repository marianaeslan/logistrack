package br.com.logistrack.controller;

import br.com.logistrack.dto.usuario.AuthResponseDTO;
import br.com.logistrack.dto.usuario.AuthenticationDTO;
import br.com.logistrack.dto.usuario.RegisterDTO;
import br.com.logistrack.entity.Usuario;
import br.com.logistrack.infra.security.TokenService;
import br.com.logistrack.service.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authManager, UsuarioService usuarioService, TokenService tokenService) {
        this.authManager = authManager;
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }


    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid RegisterDTO data) {
        if (this.usuarioService.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        this.usuarioService.create(data);


        return ResponseEntity.ok().build();
    }
}
